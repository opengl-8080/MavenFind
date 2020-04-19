package mvnfind;

import com.fasterxml.jackson.databind.JsonNode;

public class Main {
    private static final HelpPrinter HELP_PRINTER = new HelpPrinter();
    
    public static void main(String[] args) throws Exception {
        try {
            new Main().execute(args);
        } catch (IllegalCommandLineArgsException e) {
            System.err.println("Invalid command line parameters : " + e.getMessage());
            HELP_PRINTER.print(System.err);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unknown Exception");
            e.printStackTrace(System.err);
            System.exit(2);
        }
    }
    
    private final ResultPrinter resultPrinter = new ResultPrinter();
    private final CentralRepositoryRequester requester = new CentralRepositoryRequester();
    
    private void execute(String[] args) throws Exception {
        final Arguments arguments = Arguments.parse(args);
        
        Logger.get().setDebug(arguments.isDebug());
        
        if (arguments.hasHelp()) {
            HELP_PRINTER.print(System.out);
        } else {
            final JsonNode rootNode = this.requester.send(arguments);
            this.resultPrinter.print(rootNode);
        }
    }
}
