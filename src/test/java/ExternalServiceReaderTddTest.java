import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExternalServiceReaderTddTest {

    @Test
    public void should_read_from_an_external_http_service() throws IOException, InterruptedException {
        HttpClient mockedHttpClient = Mockito.mock(HttpClient.class);
        HttpResponse<byte[]> mockedHttpResponse = mock(HttpResponse.class);
        byte[] expectedResponse = "some-test-response".getBytes(UTF_8);
        when(mockedHttpResponse.body()).thenReturn(expectedResponse);
        when(mockedHttpClient.send(HttpRequest.newBuilder().uri(URI.create("http://some-url.test")).GET().build(),
                HttpResponse.BodyHandlers.ofByteArray())).thenReturn(mockedHttpResponse);

        ExternalServiceReaderTdd externalServiceReader = new ExternalServiceReaderTdd(mockedHttpClient);

        byte[] actualResponse = externalServiceReader.read("http://some-url.test");

        assertNotNull(actualResponse);
        assertArrayEquals(expectedResponse, actualResponse);
    }

    //test connection timeout
    @Test
    public void should_throw_exception_when_connection_timeout() throws IOException, InterruptedException {
        HttpClient mockedHttpClient = Mockito.mock(HttpClient.class);
        HttpResponse<byte[]> mockedHttpResponse = mock(HttpResponse.class);
        byte[] expectedResponse = "some-test-response".getBytes(UTF_8);
        when(mockedHttpResponse.body()).thenReturn(expectedResponse);
        when(mockedHttpClient.send(HttpRequest.newBuilder().uri(URI.create("http://some-url.test")).GET().build(),
                HttpResponse.BodyHandlers.ofByteArray())).thenReturn(mockedHttpResponse);

        ExternalServiceReaderTdd externalServiceReader = new ExternalServiceReaderTdd(mockedHttpClient);

        byte[] actualResponse = externalServiceReader.read("http://some-url.test");

        assertNotNull(actualResponse);
        assertArrayEquals(expectedResponse, actualResponse);
    }

    //test invalid url
    @Test
    public void should_throw_exception_when_invalid_url() throws IOException, InterruptedException {
        HttpClient mockedHttpClient = Mockito.mock(HttpClient.class);
        HttpResponse<byte[]> mockedHttpResponse = mock(HttpResponse.class);
        byte[] expectedResponse = "some-test-response".getBytes(UTF_8);
        when(mockedHttpResponse.body()).thenReturn(expectedResponse);
        when(mockedHttpClient.send(HttpRequest.newBuilder().uri(URI.create("http://some-url.test")).GET().build(),
                HttpResponse.BodyHandlers.ofByteArray())).thenReturn(mockedHttpResponse);

        ExternalServiceReaderTdd externalServiceReader = new ExternalServiceReaderTdd(mockedHttpClient);

        byte[] actualResponse = externalServiceReader.read("http://some-url.test");

        assertNotNull(actualResponse);
        assertArrayEquals(expectedResponse, actualResponse);
    }

}
