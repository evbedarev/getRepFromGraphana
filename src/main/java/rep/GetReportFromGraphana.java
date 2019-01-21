package rep;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        for (String url : listUrls) {
            Image image = Image.getInstance(url);
            System.out.println("added");
            document.add(image);
        }
        document.close();
    }



}
