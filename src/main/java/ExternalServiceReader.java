import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class ExternalServiceReader {

    private static final HttpClient oldhttpClient = HttpClient.newHttpClient();
    private final HttpClient httpClient;


    public static CompletableFuture<byte[]> readFromExternalService(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return oldhttpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(HttpResponse::body);
    }

    public static void main(String[] args) throws Exception {
        String url = "https://example.com";
        CompletableFuture<byte[]> response = readFromExternalService(url);
        byte[] responseBody = response.join();
        String responseBodyString = new String(responseBody, StandardCharsets.UTF_8);
        System.out.println(responseBodyString);
    }

    public ExternalServiceReader(final HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public byte[] read(final String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
    }
}
