import org.junit.Test;
import rep.GetReportFromGraphana;

import java.util.ArrayList;
import java.util.List;

public class TestJU {

//    @org.junit.Test
//    public void test() throws Exception {
//        List<String> strings = new ArrayList<>();
//        strings.add("https://avatars.mds.yandex.net/get-pdb/932587/260efa1d-862e-4b53-937c-c7149df36336/s1200?webp=false");
//        GetReportFromGraphana getReportFromGraphana = new GetReportFromGraphana(strings, "/home/");
//        Assert.assertTrue(getReportFromGraphana.saveReport());
//    }

    @Test
    public void saveToPdf() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("https://avatars.mds.yandex.net/get-pdb/932587/260efa1d-862e-4b53-937c-c7149df36336/s1200?webp=false");
        strings.add("https://zone-cs.ru/files/forums_imgs/1540997310.png");
        GetReportFromGraphana getReportFromGraphana = new GetReportFromGraphana(strings, "/home/");
        getReportFromGraphana.savePngToPdf();

    }


}
