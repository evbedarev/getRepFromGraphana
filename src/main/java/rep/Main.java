package rep;

import java.util.ArrayList;
import java.util.List;

/**
 * http://st1-3.vm.esrt.cloud.sbrf.ru:3000/d/0bEaq8Eik/stresstesting-allnodes-network?orgId=1&from=1547808313859&to=1547809213859&var-node=10.36.130.145%3A9100
 * arg -b - begin time
 * arg -e - end time
 * arg -f -  file to save
 * arg -path - path to file example '/home/mj/'
 */
public class Main {
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
                    .replaceAll("&from=", "&from=" + args[argBeginTime + 1])
                    .replaceAll("&to=", "&to=" + args[argEndTime + 1])
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
