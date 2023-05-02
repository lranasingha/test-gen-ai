import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.itextpdf.text.DocumentException;

class PdfGeneratorTest {

    @Test
    void testGeneratePdf() throws Exception {
        String content = "This is a test PDF document.";
        byte[] pdfBytes = PdfGenerator.generatePdf(content);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(pdfBytes);

        assertTrue(outputStream.size() > 0);
        assertEquals(content, pdfBytes);
    }

    @Test
    void testGeneratePdfEmptyContent() throws Exception {
        String content = "";
        byte[] pdfBytes = PdfGenerator.generatePdf(content);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(pdfBytes);

        assertTrue(outputStream.size() > 0);
    }

    @Test
    void testGeneratePdfNullContent() throws IOException, DocumentException {
        assertThrows(NullPointerException.class, () -> {
            PdfGenerator.generatePdf(null);
        });
    }

}