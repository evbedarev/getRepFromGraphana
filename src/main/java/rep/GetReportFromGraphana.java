package rep;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetReportFromGraphana {
    private List<String> listUrls;
    private String pathToSave;
    private List<String> nameFiles;
    private String keyBearer;

    public GetReportFromGraphana(List<String> listUrls,
                                 String pathToSave,
                                 String keyBearer) {
        this.listUrls = listUrls;
        this.pathToSave = pathToSave;
        this.keyBearer = keyBearer;
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

    public String generateNameReport(String link) {
//        Pattern pattern = Pattern.compile("/((?:[a-zA-Z]|-)*)(?:(?:\\?panelId)|(?:\\?orgId))");
        Pattern pattern = Pattern.compile("var-host=(\\d*\\.\\d*\\.\\d*\\.\\d*)&");
        Matcher matcher = pattern.matcher(link);
        if (matcher.find())
            return matcher.group(1);
        return "noNameReport";
    }

    public void savePngToPdf() throws Exception {
        Document document = new Document();
        long timeCreatePdf = System.currentTimeMillis();
        PdfWriter.getInstance(document, new FileOutputStream(pathToSave + timeCreatePdf + ".pdf"));
        document.open();
        LinkedHashMap<String, String> pngFileMap = downloadFileUsingCurl(listUrls);
        for (Map.Entry<String,String> pngFile: pngFileMap.entrySet()) {
            Image image = Image.getInstance(pngFile.getKey());
            document.add(new Paragraph(pngFile.getValue()));
            document.add(image);
            System.out.println(pngFile.getKey() + " " + pngFile.getValue());
        }
        document.close();
    }

    //curl -H "Authorization: Bearer eyJrIjoiYzNqd285RkZIQ0EwSkYwUVJBQzFRaTU1NFdTYTZnZTYiLCJuIjoiZXhwb3J0IiwiaWQiOjF9" http://st1-3.vm.esrt.cloud.sbrf.ru:3000/api/dashboards/home
    public LinkedHashMap<String,String> downloadFileUsingCurl(List<String> urls) throws IOException {
        LinkedHashMap<String, String> pngFileMap = new LinkedHashMap<>();
        System.out.println(keyBearer);
        for (String urlLink : urls) {
            URL url = new URL(urlLink);
            System.out.println(urlLink);
            URLConnection uc = url.openConnection();
            uc.setRequestProperty("Authorization", "Bearer " + keyBearer);
            InputStream inputStream = uc.getInputStream();
            if (inputStream != null) {
                String fileName = System.currentTimeMillis() + ".png";
                pngFileMap.put(pathToSave + fileName, generateNameReport(urlLink));
                Files.copy(inputStream, Paths.get(pathToSave + fileName));
            } else {
                throw new NullPointerException("Cant download png from graphana...");
            }
        }
        return pngFileMap;
    }
}
