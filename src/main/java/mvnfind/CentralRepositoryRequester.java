package mvnfind;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CentralRepositoryRequester {
    private static final Logger LOGGER = Logger.get();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public JsonNode send(Arguments arguments) throws IOException, InterruptedException {
        final String query = arguments.hasAdvancedOptions()
                ? this.buildAdvancedQuery(arguments)
                : this.buildClassicQuery(arguments);

        LOGGER.debug(() -> "query = " + query);

        URIBuilder uriBuilder = URIBuilder.of("https://search.maven.org/solrsearch/select")
                .addQueryParameter("wt", "json")
                .addQueryParameter("rows", arguments.getRows().orElse(10))
                .addQueryParameter("q", query);
        
        if (arguments.isAllVersions()) {
            uriBuilder.addQueryParameter("core", "gav");
        }
        arguments.getStart().ifPresent(start -> uriBuilder.addQueryParameter("start", start));

        final URI uri = uriBuilder.build();
        LOGGER.debug(() -> "uri = " + uri);

        final HttpRequest request = HttpRequest.newBuilder(uri).GET().build();

        final HttpClient httpClient = this.buildClient(arguments);
        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        LOGGER.debug(response::body);
        return objectMapper.readTree(response.body());
    }
    
    private String buildAdvancedQuery(Arguments arguments) {
        List<String> queries = new ArrayList<>();
        arguments.getGroupId().map(groupId -> formatAdvancedQuery("g", groupId)).ifPresent(queries::add);
        arguments.getArtifactId().map(artifactId -> formatAdvancedQuery("a", artifactId)).ifPresent(queries::add);
        arguments.getVersion().map(version -> formatAdvancedQuery("v", version)).ifPresent(queries::add);
        arguments.getPackaging().map(packaging -> formatAdvancedQuery("p", packaging)).ifPresent(queries::add);
        arguments.getClassifier().map(classifier -> formatAdvancedQuery("l", classifier)).ifPresent(queries::add);
        arguments.getClassName().map(className -> formatAdvancedQuery("c", className)).ifPresent(queries::add);
        arguments.getFullClassName().map(fullClassName -> formatAdvancedQuery("f", fullClassName)).ifPresent(queries::add);
        arguments.getSha1().map(sha1 -> formatAdvancedQuery("1", sha1)).ifPresent(queries::add);

        return String.join(" AND ", queries);
    }

    private String formatAdvancedQuery(String key, String value) {
        return key + ":\"" + value + "\"";
    }

    private String buildClassicQuery(Arguments arguments) {
        return arguments.getQuery().orElseThrow(() -> new IllegalCommandLineArgsException("query is not specified."));
    }

    private HttpClient buildClient(Arguments arguments) {
        final HttpClient.Builder clientBuilder = HttpClient.newBuilder();
        
        arguments.getHttpProxy().ifPresent(httpProxy -> {
            final String proxyHost = arguments.getProxyHost().orElseThrow();
            final int proxyPort = arguments.getProxyPort().orElseThrow();
            final InetSocketAddress proxyAddress = InetSocketAddress.createUnresolved(proxyHost, proxyPort);
            clientBuilder.proxy(ProxySelector.of(proxyAddress));

            arguments.getProxyUser().ifPresent(proxyUser -> {
                final String proxyPass = arguments.getProxyPass().orElse("");
                final Authenticator authenticator = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(proxyUser, proxyPass.toCharArray());
                    }
                };
                clientBuilder.authenticator(authenticator);
            });
        });

        return clientBuilder.build();
    }
}
