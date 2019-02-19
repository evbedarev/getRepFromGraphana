import org.junit.Test;
import rep.CopyPdfInArtifacts;

import java.io.IOException;


public class TestCopyFile {

    @Test
    public void testCopy() throws IOException {
        CopyPdfInArtifacts.copyPdfToBuildAndDeleteFromSource("C:\\TEMP\\55");
    }


}
