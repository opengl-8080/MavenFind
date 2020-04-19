package mvnfind;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.regex.Pattern;

public class Arguments {
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^0|[1-9][0-9]*$");

    public static Arguments parse(String... args) {
        final Arguments arguments = new Arguments();
        for (int i=0; i<args.length; i++) {
            final String token = args[i];
            
            if (token.equals("-h") || token.equals("--help")) {
                arguments.help = true;
            } else if (token.equals("-g") || token.equals("--groupId")) {
                arguments.groupId = requiredParameter("groupId", args, ++i);
            } else if (token.equals("-a") || token.equals("--artifactId")) {
                arguments.artifactId = requiredParameter("artifactId", args, ++i);
            } else if (token.equals("-v") || token.equals("--version")) {
                arguments.version = requiredParameter("version", args, ++i);
            } else if (token.equals("-p") || token.equals("--packaging")) {
                arguments.packaging = requiredParameter("packaging", args, ++i);
            } else if (token.equals("-l") || token.equals("--classifier")) {
                arguments.classifier = requiredParameter("classifier", args, ++i);
            } else if (token.equals("-c") || token.equals("--className")) {
                arguments.className = requiredParameter("className", args, ++i);
            } else if (token.equals("-f") || token.equals("--fullClassName")) {
                arguments.fullClassName = requiredParameter("fullClassName", args, ++i);
            } else if (token.equals("-1") || token.equals("--sha1")) {
                arguments.sha1 = requiredParameter("sha1", args, ++i);
            } else if (token.equals("-s") || token.equals("--start")) {
                final int start = requiredIntParameter("start", args, ++i);
                if (start < 0) {
                    throw new IllegalCommandLineArgsException("start must be greater than equal 0.");
                }
                arguments.start = start;
            } else if (token.equals("-r") || token.equals("--rows")) {
                final int rows = requiredIntParameter("rows", args, ++i);
                if (rows < 0) {
                    throw new IllegalCommandLineArgsException("rows must be greater than 0.");
                }
                arguments.rows = rows;
            } else if (token.equals("--httpProxy")) {
                final String httpProxy = requiredParameter("httpProxy", args, ++i);
                final String[] hostAndPort = httpProxy.split(":");
                if (hostAndPort.length != 2) {
                    throw new IllegalCommandLineArgsException("httpProxy must be \"host:port\".");
                }
                if (!INTEGER_PATTERN.matcher(hostAndPort[1]).matches()) {
                    throw new IllegalCommandLineArgsException("httpProxy port must be integer.");
                }
                
                arguments.httpProxy = httpProxy;
                arguments.proxyHost = hostAndPort[0];
                arguments.proxyPort = Integer.valueOf(hostAndPort[1]);
            } else if (token.equals("--proxyUser")) {
                arguments.proxyUser = requiredParameter("proxyUser", args, ++i);
            } else if (token.equals("--proxyPass")) {
                arguments.proxyPass = requiredParameter("proxyPass", args, ++i);
            } else if (token.equals("--debug")) {
                arguments.debug = true;
            } else if (token.equals("--all")) {
                arguments.allVersions = true;
            } else {
                arguments.query = token;
            }
        }
        
        return arguments;
    }
    
    private static String requiredParameter(String name, String[] args, int index) {
        if (args.length <= index) {
            throw new IllegalCommandLineArgsException(name + " must have a parameter.");
        }
        return args[index];
    }
    
    private static int requiredIntParameter(String name, String[] args, int index) {
        if (args.length <= index) {
            throw new IllegalCommandLineArgsException(name + " must have a parameter.");
        }
        final String text = args[index];
        if (INTEGER_PATTERN.matcher(text).matches()) {
            return Integer.parseInt(text);
        } else {
            throw new IllegalCommandLineArgsException(name + " must be integer.");
        }
    }
    
    private boolean help;
    private String groupId;
    private String artifactId;
    private String version;
    private String packaging;
    private String classifier;
    private String className;
    private String fullClassName;
    private String sha1;
    private Integer rows;
    private Integer start;
    private boolean allVersions;
    private String httpProxy;
    private String proxyHost;
    private Integer proxyPort;
    private String proxyUser;
    private String proxyPass;
    
    private String query;
    
    private boolean debug;

    private Arguments() {}

    public boolean hasHelp() {
        return help;
    }

    public Optional<String> getGroupId() {
        return Optional.ofNullable(groupId);
    }

    public Optional<String> getArtifactId() {
        return Optional.ofNullable(artifactId);
    }

    public Optional<String> getVersion() {
        return Optional.ofNullable(version);
    }

    public Optional<String> getPackaging() {
        return Optional.ofNullable(packaging);
    }

    public Optional<String> getClassifier() {
        return Optional.ofNullable(classifier);
    }

    public Optional<String> getClassName() {
        return Optional.ofNullable(className);
    }

    public Optional<String> getFullClassName() {
        return Optional.ofNullable(fullClassName);
    }

    public Optional<String> getSha1() {
        return Optional.ofNullable(sha1);
    }

    public OptionalInt getRows() {
        return rows != null ? OptionalInt.of(rows) : OptionalInt.empty();
    }

    public Optional<String> getHttpProxy() {
        return Optional.ofNullable(httpProxy);
    }
    
    public Optional<String> getProxyHost() {
        return Optional.ofNullable(proxyHost);
    }

    public OptionalInt getProxyPort() {
        return proxyPort != null ? OptionalInt.of(proxyPort) : OptionalInt.empty();
    }
    
    public Optional<String> getProxyUser() {
        return Optional.ofNullable(proxyUser);
    }

    public Optional<String> getProxyPass() {
        return Optional.ofNullable(proxyPass);
    }

    public Optional<String> getQuery() {
        return Optional.ofNullable(query);
    }

    public boolean hasAdvancedOptions() {
        return this.getGroupId().isPresent()
                || this.getArtifactId().isPresent()
                || this.getVersion().isPresent()
                || this.getPackaging().isPresent()
                || this.getClassifier().isPresent()
                || this.getClassName().isPresent()
                || this.getFullClassName().isPresent()
                || this.getSha1().isPresent();
    }

    public boolean isDebug() {
        return debug;
    }

    public OptionalInt getStart() {
        return start != null ? OptionalInt.of(start) : OptionalInt.empty();
    }

    public boolean isAllVersions() {
        return allVersions;
    }
}
