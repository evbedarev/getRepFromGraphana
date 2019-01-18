package rep;

import java.util.ArrayList;
import java.util.List;

/**
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

        /*checks arguments*/
        for (String arg : args) {
            if (arg.equals("-b") )
                ArgBeginTime = countArgs;
            if (arg.equals("-e"))
                ArgEndTime = countArgs;
            countArgs++;
        }
        if (ArgBeginTime == 0 || ArgEndTime == 0) throw new IllegalArgumentException("no parametr -b or -e");
        if (!args[ArgBeginTime+1].matches("[0-9]*") || !args[ArgEndTime+1].matches("[0-9]*") )
            throw new IllegalArgumentException("Invalid argument, must be numeric");


        /*generate links */
        List<String> graphanaUrls = new ArrayList<String>();
        for (String arg : args) {
            if (arg.equals("-b"))
                break;
            graphanaUrls.add(arg + args[ArgBeginTime+1] + ":" + args[ArgEndTime+1]);
            System.out.println(arg + args[ArgBeginTime+1] + ":" + args[ArgEndTime+1]);
        }

        GetReportFromGraphana getReportFromGraphana = new GetReportFromGraphana(graphanaUrls,
                "/home/"
                );
        getReportFromGraphana.saveReport();

    }
}
