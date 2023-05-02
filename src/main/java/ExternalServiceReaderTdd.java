import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class ExternalServiceReaderTdd {
    private final HttpClient httpClient;

    public ExternalServiceReaderTdd(final HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public byte[] read(final String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return httpClient.send(request, BodyHandlers.ofByteArray()).body();
    }

}
