package mvnfind;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public class HelpPrinter {

    public void print(PrintStream ps) {
        ps.println(this.readResourceTest("/help.txt"));
    }

    private String readResourceTest(String path) {
        try (
                final BufferedInputStream in = new BufferedInputStream(this.getClass().getResourceAsStream(path));
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            in.transferTo(out);
            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
