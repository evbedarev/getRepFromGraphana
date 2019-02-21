import org.junit.Test;
import rep.CopyPdfInArtifacts;

import java.io.IOException;


public class TestCopyFile {

    @Test
    public void testCopy() throws IOException {
        CopyPdfInArtifacts copyPdfInArtifacts = new CopyPdfInArtifacts();
        String lastFolder = copyPdfInArtifacts.findNumericDirAndSort("/home/mj/");
        copyPdfInArtifacts.copyPdfToBuildAndDeleteFromSource(lastFolder);
    }
}
