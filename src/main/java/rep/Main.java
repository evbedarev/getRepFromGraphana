package rep;

import java.util.ArrayList;
import java.util.List;

/**
 * arg -b - begin time
 * arg -e - end timeПрр
 * arg -f -  file to save
 * arg -path - path to file example '/home/mj/'
 * arg -key - Authorization key from grafana to view.
 */
public class Main {
    private static List<Integer> countArgsList = new ArrayList<>();
    private static int argBeginTime = 0;
    private static int argEndTime = 0;
    private static int argPathToSave = 0;
    private static int argKey = 0;
    private static int countArgs = 0;
    private static long beginTime = 0;
    private static long endTime = 0;

    public static void main(String[] args) throws Exception {
        checkArgs(args);
        //calculate time
        long currntTime = System.currentTimeMillis();
        beginTime = currntTime - Long.parseLong(args[argBeginTime + 1]) * 60 * 1000;
        endTime = currntTime - Long.parseLong(args[argEndTime + 1]) * 60 * 1000;

        /*generate links */
        List<String> graphanaUrls = new ArrayList<>();
        for (String arg : args) {
            if (arg.equals("-b"))
                break;
            graphanaUrls.add(arg
                    .replaceAll("&from=\\d*", "&from=" + beginTime)
                    .replaceAll("&to=\\d*", "&to=" + endTime)
                    .replaceAll("&height=\\d*", "&height=300")
                    .replaceAll("&width=\\d*", "&width=500")
            );
        }

        GetReportFromGraphana getReportFromGraphana = new GetReportFromGraphana(
                graphanaUrls,
                args[argPathToSave + 1],
                args[argKey + 1]);
        getReportFromGraphana.savePngToPdf();
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
            if (arg.equals("-key"))
                argKey = countArgs;
            countArgs++;
        }
        if (argBeginTime == 0 || argEndTime == 0 || argPathToSave == 0 || argKey == 0)
            throw new IllegalArgumentException("no parametr -b or -e or -path or -path_to_aft");
        if (!args[argBeginTime + 1].matches("[0-9]*") || !args[argEndTime + 1].matches("[0-9]*"))
            throw new IllegalArgumentException("Invalid argument, must be numeric");
    }

}
