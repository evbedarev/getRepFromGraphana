package rep;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import javax.swing.text.html.parser.Parser;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class GetReportFromGraphana {
    private List<String> listUrls;
    private String pathToSave;
    private List<String> nameFiles;

    public GetReportFromGraphana(List<String> listUrls,
                                 String pathToSave) {
        this.listUrls = listUrls;
        this.pathToSave = pathToSave;
    }

    public boolean saveReport() throws Exception {
        for (String url : listUrls) {
            try (InputStream in = URI.create(url).toURL().openStream()) {
                String pathFile = pathToSave + System.currentTimeMillis() + ".png";
                Files.copy(in, Paths.get(pathFile));
                nameFiles.add(pathFile);
            }
        }
        return true;
    }

    public void savePngToPdf() throws Exception {
        Document document = new Document();
        long timeCreatePdf = System.currentTimeMillis();
        PdfWriter.getInstance(document, new FileOutputStream(pathToSave + timeCreatePdf + ".pdf"));
        document.open();
        for (String url : downloadFileUsingCurl(listUrls)) {
            Image image = Image.getInstance(url);
            System.out.println("added");
            document.add(image);
        }
        document.close();
    }

    //curl -H "Authorization: Bearer eyJrIjoiYzNqd285RkZIQ0EwSkYwUVJBQzFRaTU1NFdTYTZnZTYiLCJuIjoiZXhwb3J0IiwiaWQiOjF9" http://st1-3.vm.esrt.cloud.sbrf.ru:3000/api/dashboards/home
    public List<String> downloadFileUsingCurl(List<String> urls) throws IOException {
        List<String> pngFiles = new ArrayList<>();
        for (String urlLink : urls) {
            URL url = new URL(urlLink);
            URLConnection uc = url.openConnection();
            uc.setRequestProperty("Authorization", "Bearer " + "eyJrIjoiYzNqd285RkZIQ0EwSkYwUVJBQzFRaTU1NFdTYTZnZTYiLCJuIjoiZXhwb3J0IiwiaWQiOjF9");
            InputStream inputStream = uc.getInputStream();
            if (inputStream != null) {
                String fileName = System.currentTimeMillis() + ".png";
                pngFiles.add(pathToSave + fileName);
                Files.copy(inputStream, Paths.get(pathToSave + fileName));
            }
        }
        return pngFiles;
    }



}
