package rep;

import java.util.Comparator;

public class CompareDirs implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Integer num1 = Integer.parseInt((String) o1);
        Integer num2 = Integer.parseInt((String) o2);
        return num1 > num2 ? 1 : num1 == num2 ? 0 : -1;
    }
}
