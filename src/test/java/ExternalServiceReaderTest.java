import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExternalServiceReaderTest {

    private ExternalServiceReader externalServiceReader;
    private HttpClient httpClient;
    private HttpResponse<String> httpResponse;

    @BeforeEach
    public void setup() {
        externalServiceReader = new ExternalServiceReader(httpClient);
        httpClient = mock(HttpClient.class);
        httpResponse = mock(HttpResponse.class);
    }

    @Test
    public void testReadFromExternalServiceReturnsResponseBody() throws URISyntaxException, IOException, InterruptedException {
        String responseBody = "Response from external service";
        when(httpResponse.body()).thenReturn(responseBody);
        CompletableFuture<HttpResponse<String>> httpResponseFuture = CompletableFuture.completedFuture(httpResponse);
        when(httpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponseFuture);

        CompletableFuture<byte[]> result = externalServiceReader.readFromExternalService("https://example.com");

        assertEquals(responseBody, result);
    }

    @Test
    public void testReadFromExternalServiceThrowsIOException() throws URISyntaxException, IOException, InterruptedException {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(IOException.class);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(IOException.class, () -> {
            externalServiceReader.readFromExternalService("https://example.com");
        });

        assertEquals(IOException.class, exception.getClass());
    }

    @Test
    public void testReadFromExternalServiceThrowsInterruptedException() throws URISyntaxException, IOException, InterruptedException {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(InterruptedException.class);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(InterruptedException.class, () -> {
            externalServiceReader.readFromExternalService("https://example.com");
        });

        assertEquals(InterruptedException.class, exception.getClass());
    }

    @Test
    public void testReadFromExternalServiceThrowsURISyntaxException() throws URISyntaxException, IOException, InterruptedException {
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(URISyntaxException.class, () -> {
            externalServiceReader.readFromExternalService(":");
        });

        assertEquals(URISyntaxException.class, exception.getClass());
    }
}
