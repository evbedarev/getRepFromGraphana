package rep;

import java.util.ArrayList;
import java.util.List;

/**
 * arg -b - begin time
 * arg -e - end time
 * arg -f -  file to save
 * arg -path - path to file example '/home/mj/'
 * arg -path_to_build '/home/mj/'
 */
public class Main {
    private static List<Integer> countArgsList = new ArrayList<>();
    private static int argBeginTime = 0;
    private static int argEndTime = 0;
    private static int argPathToSave = 0;
    private static int countArgs = 0;

    public static void main(String[] args) throws Exception {
        checkArgs(args);
        /*generate links */
        List<String> graphanaUrls = new ArrayList<>();
        for (String arg : args) {
            if (arg.equals("-b"))
                break;
            graphanaUrls.add(arg
                    .replaceAll("&from=\\d*", "&from=" + args[argBeginTime + 1] )
                    .replaceAll("&to=\\d*", "&to=" + args[argEndTime + 1] )
                    .replaceAll("&height=\\d*", "&height=300")
                    .replaceAll("&width=\\d*", "&width=500")
            );
        }

        GetReportFromGraphana getReportFromGraphana = new GetReportFromGraphana(
                graphanaUrls,
                args[argPathToSave + 1]);
        getReportFromGraphana.savePngToPdf();
//        getReportFromGraphana.downloadFileUsingCurl();
    }

    /*check arguments*/
    private static void checkArgs(String[] args) {
        for (String arg : args) {
            if (arg.equals("-b") )
                argBeginTime = countArgs;
            if (arg.equals("-e"))
                argEndTime = countArgs;
            if (arg.equals("-path"))
                argPathToSave = countArgs;
            countArgs++;
        }
        if (argBeginTime == 0 || argEndTime == 0 || argPathToSave == 0)
            throw new IllegalArgumentException("no parametr -b or -e or -path");
        if (!args[argBeginTime + 1].matches("[0-9]*") || !args[argEndTime + 1].matches("[0-9]*") )
            throw new IllegalArgumentException("Invalid argument, must be numeric");
    }
}
