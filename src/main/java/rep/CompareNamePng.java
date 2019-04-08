package rep;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompareNamePng implements Comparator {

    @Override
    public int compare(Object o, Object o1) {
        Long long1 = 0l;
        Long long2 = 0l;
        Pattern pattern = Pattern.compile("\\./(\\d*)\\.png");
        Matcher matcher1 =  pattern.matcher((String) o);
        Matcher matcher2 = pattern.matcher((String) o1);
        if (matcher1.find())
            long1 = Long.parseLong(matcher1.group(1));
        if (matcher2.find())
            long2 = Long.parseLong(matcher2.group(1));

        return long1 > long2 ? 1 : long1 == long2 ? 0 : -1;
    }
}
