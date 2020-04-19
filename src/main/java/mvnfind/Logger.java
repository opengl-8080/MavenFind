package mvnfind;

import java.util.function.Supplier;

public class Logger {
    private static final Logger instance = new Logger();
    
    public static Logger get() {
        return instance;
    }
    
    private boolean debug;
    
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    public void debug(Supplier<String> supplier) {
        if (debug) {
            System.out.println(supplier.get());
        }
    }
    
    public void info(String message) {
        System.out.println(message);
    }
}
