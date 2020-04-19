package mvnfind;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class URIBuilder {
    
    private final String base;
    private final List<String> queryParameters = new ArrayList<>();
    
    public static URIBuilder of(String baseUrl) {
        return new URIBuilder(baseUrl);
    }

    private URIBuilder(String base) {
        this.base = base;
    }

    public URIBuilder addQueryParameter(String key, String value) {
        queryParameters.add(urlEncode(key) + "=" + urlEncode(value));
        return this;
    }

    public URIBuilder addQueryParameter(String key, int value) {
        queryParameters.add(urlEncode(key) + "=" + urlEncode(String.valueOf(value)));
        return this;
    }
    
    public URI build() {
        if (queryParameters.isEmpty()) {
            return URI.create(this.base);
        } else {
            return URI.create(this.base + "?" + String.join("&", this.queryParameters));
        }
    }
    
    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
