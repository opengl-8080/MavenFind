package mvnfind;

import com.fasterxml.jackson.databind.JsonNode;

public class ResultPrinter {
    
    public void print(JsonNode rootNode) {
        final JsonNode responseNode = rootNode.get("response");
        for (JsonNode doc : responseNode.get("docs")) {
            final String groupId = doc.get("g").asText();
            final String artifactId = doc.get("a").asText();
            final String version = this.obtainVersion(doc);
            System.out.printf("%s:%s:%s%n", groupId, artifactId, version);
        }

        final int rows = responseNode.get("docs").size();
        final int numFound = responseNode.get("numFound").asInt();
        final int start = responseNode.get("start").asInt();
        System.out.printf("Offset : %d-%d, Total : %d%n", start, (start + rows), numFound);
    }
    
    private String obtainVersion(JsonNode doc) {
        final JsonNode latestVersion = doc.get("latestVersion");
        if (latestVersion == null) {
            return doc.get("v").asText();
        } else {
            return latestVersion.asText();
        }
    }
}
