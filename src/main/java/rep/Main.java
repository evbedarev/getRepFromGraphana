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
    public static void main(String[] args) throws Exception {
        int ArgBeginTime = 0;
        int ArgEndTime = 0;
        StringBuffer sb = new StringBuffer();
        int countArgs = 0;

        /*check arguments*/
        for (String arg : args) {
            if (arg.equals("-b") )
                ArgBeginTime = countArgs;
            if (arg.equals("-e"))
                ArgEndTime = countArgs;
            countArgs++;
        }
        if (ArgBeginTime == 0 || ArgEndTime == 0)
            throw new IllegalArgumentException("no parametr -b or -e");
        if (!args[ArgBeginTime+1].matches("[0-9]*") || !args[ArgEndTime+1].matches("[0-9]*") )
            throw new IllegalArgumentException("Invalid argument, must be numeric");


        /*generate links */
        List<String> graphanaUrls = new ArrayList<String>();
        for (String arg : args) {
            if (arg.equals("-b"))
                break;
            graphanaUrls.add(arg + args[ArgBeginTime+1] + ":" + args[ArgEndTime+1]);
            graphanaUrls.add(arg
                    .replaceAll("&from=", "&from=" + args[ArgBeginTime+1])
                    .replaceAll("&to=", "&to=" + args[ArgEndTime+1])
            );
        }

        GetReportFromGraphana getReportFromGraphana = new GetReportFromGraphana(graphanaUrls,
                "/home/"
                );
        getReportFromGraphana.saveReport();

    }
}
