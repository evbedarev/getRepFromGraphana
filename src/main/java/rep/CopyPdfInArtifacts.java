package rep;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * work with pdf files, copy from current directory and put it to last build folder
 * then delete all pdf and png files from current folder
 */

public class CopyPdfInArtifacts {

    public String findNumericDirAndSort(String buildPath) throws IOException {
        List<Path> paths = Files.walk(Paths.get(buildPath), 1)
                .filter(Files::isDirectory)
                .collect(Collectors.toList());
        List<String> numDirs = new ArrayList<>();

        Pattern pattern = Pattern.compile("/(\\d+)");
        for (Path path : paths) {
            Matcher matcher = pattern.matcher(path.toString());
            if (matcher.find()) {
                numDirs.add(matcher.group(1));
            }
        }
        numDirs.sort(new CompareDirs());
        return buildPath + numDirs.get(numDirs.size() - 1);
    }

    public void copyPdfToBuildAndDeleteFromSource(String pathToCopy) throws IOException {
        System.out.println(Paths.get(new File("").getAbsoluteFile().toString()));
        List<Path> files = Files.walk(Paths.get(new File("").getAbsoluteFile().toString()), 1)
                .filter(path -> path.toString().matches("(?:/.*\\.pdf)||(?:/.*\\.png)"))
                .collect(Collectors.toList());
        System.out.println(files.size());
        for (Path file : files) {
            if (file.toString().matches("/.*\\.pdf"))
                Files.copy(file,
                    Paths.get(pathToCopy + "/" + System.currentTimeMillis() + ".pdf"));
            Files.delete(file);
        }
    }
}
