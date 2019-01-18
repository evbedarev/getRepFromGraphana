package rep;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GetReportFromGraphana {
    private List<String> listUrls;
    private String pathToSave;

    public GetReportFromGraphana(List<String> listUrls,
                                 String pathToSave) {
        this.listUrls = listUrls;
        this.pathToSave = pathToSave;
    }

    public boolean saveReport() throws Exception {
        for (String url : listUrls) {
            try (InputStream in = URI.create(url).toURL().openStream()) {
                Files.copy(in, Paths.get(pathToSave + System.currentTimeMillis() + ".png"));
            }
        }
        return true;
    }



}
