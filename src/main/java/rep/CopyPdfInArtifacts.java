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

    public static List<Path> findAllDirs(String path) throws IOException {
        return Files.walk(Paths.get(path), 1)
                .filter(Files::isDirectory)
                .collect(Collectors.toList());
    }

    public static List<String> findNumericDirAndSort(List<Path> paths, String rootDir) {
        List<String> numDirs = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\\\(\\d+)");
        for (Path path : paths) {
            Matcher matcher = pattern.matcher(path.toString());
            if (matcher.find()) {
                numDirs.add(matcher.group(1));
            }
        }
        numDirs.sort(new CompareDirs());
        return numDirs;
    }

    public static void copyPdfToBuildAndDeleteFromSource(String pathToCopy) throws IOException {
        List<Path> files = Files.walk(Paths.get(new File("").getAbsoluteFile().toString()), 1)
                .filter(path -> path.toString().matches("(?:.*\\.pdf)||(?:.*\\.png)"))
                .collect(Collectors.toList());
        for (Path file : files) {
            if (file.toString().matches(".*\\.pdf"))
                Files.copy(file,
                    Paths.get(pathToCopy + "\\" + System.currentTimeMillis() + ".pdf"));
            Files.delete(file);
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> dirs;
        dirs = findNumericDirAndSort(findAllDirs("c:\\TEMP\\"), "c:\\TEMP\\");
        System.out.println(dirs.get(dirs.size() - 1));
        System.out.println();
    }
}
