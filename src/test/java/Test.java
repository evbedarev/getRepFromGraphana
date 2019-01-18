import org.junit.*;
import rep.GetReportFromGraphana;

import java.util.ArrayList;
import java.util.List;

public class Test {

    @org.junit.Test
    public void test() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("https://avatars.mds.yandex.net/get-pdb/932587/260efa1d-862e-4b53-937c-c7149df36336/s1200?webp=false");
        GetReportFromGraphana getReportFromGraphana = new GetReportFromGraphana(strings, "/home/");
        Assert.assertTrue(getReportFromGraphana.saveReport());
    }
}
