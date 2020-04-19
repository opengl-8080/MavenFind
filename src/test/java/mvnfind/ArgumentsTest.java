package mvnfind;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ArgumentsTest {

    @Test
    void help_short() {
        final Arguments arguments = Arguments.parse("-h");
        assertThat(arguments.hasHelp()).isTrue();
    }

    @Test
    void help_long() {
        final Arguments arguments = Arguments.parse("--help");
        assertThat(arguments.hasHelp()).isTrue();
    }

    @Test
    void help_is_not_advanced_options() {
        final Arguments arguments = Arguments.parse("--help", "foo");
        assertThat(arguments.hasAdvancedOptions()).isFalse();
    }

    @Test
    void groupId_short() {
        final Arguments arguments = Arguments.parse("-g", "foo");
        assertThat(arguments.getGroupId()).hasValue("foo");
    }

    @Test
    void groupId_long() {
        final Arguments arguments = Arguments.parse("--groupId", "foo");
        assertThat(arguments.getGroupId()).hasValue("foo");
    }

    @Test
    void groupId_is_advanced_options() {
        final Arguments arguments = Arguments.parse("--groupId", "foo");
        assertThat(arguments.hasAdvancedOptions()).isTrue();
    }

    @Test
    void groupId_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-g"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("groupId must have a parameter.");
    }

    @Test
    void artifactId_short() {
        final Arguments arguments = Arguments.parse("-a", "foo");
        assertThat(arguments.getArtifactId()).hasValue("foo");
    }

    @Test
    void artifactId_long() {
        final Arguments arguments = Arguments.parse("--artifactId", "foo");
        assertThat(arguments.getArtifactId()).hasValue("foo");
    }

    @Test
    void artifactId_is_advanced_options() {
        final Arguments arguments = Arguments.parse("--artifactId", "foo");
        assertThat(arguments.hasAdvancedOptions()).isTrue();
    }

    @Test
    void artifactId_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-a"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("artifactId must have a parameter.");
    }

    @Test
    void version_short() {
        final Arguments arguments = Arguments.parse("-v", "foo");
        assertThat(arguments.getVersion()).hasValue("foo");
    }

    @Test
    void version_long() {
        final Arguments arguments = Arguments.parse("--version", "foo");
        assertThat(arguments.getVersion()).hasValue("foo");
    }

    @Test
    void version_is_advanced_options() {
        final Arguments arguments = Arguments.parse("--version", "foo");
        assertThat(arguments.hasAdvancedOptions()).isTrue();
    }

    @Test
    void version_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-v"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("version must have a parameter.");
    }

    @Test
    void packaging_short() {
        final Arguments arguments = Arguments.parse("-p", "foo");
        assertThat(arguments.getPackaging()).hasValue("foo");
    }

    @Test
    void packaging_long() {
        final Arguments arguments = Arguments.parse("--packaging", "foo");
        assertThat(arguments.getPackaging()).hasValue("foo");
    }

    @Test
    void packaging_is_advanced_options() {
        final Arguments arguments = Arguments.parse("--packaging", "foo");
        assertThat(arguments.hasAdvancedOptions()).isTrue();
    }

    @Test
    void packaging_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-p"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("packaging must have a parameter.");
    }

    @Test
    void classifier_short() {
        final Arguments arguments = Arguments.parse("-l", "foo");
        assertThat(arguments.getClassifier()).hasValue("foo");
    }

    @Test
    void classifier_long() {
        final Arguments arguments = Arguments.parse("--classifier", "foo");
        assertThat(arguments.getClassifier()).hasValue("foo");
    }

    @Test
    void classifier_is_advanced_options() {
        final Arguments arguments = Arguments.parse("--classifier", "foo");
        assertThat(arguments.hasAdvancedOptions()).isTrue();
    }

    @Test
    void classifier_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-l"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("classifier must have a parameter.");
    }

    @Test
    void className_short() {
        final Arguments arguments = Arguments.parse("-c", "foo");
        assertThat(arguments.getClassName()).hasValue("foo");
    }

    @Test
    void className_long() {
        final Arguments arguments = Arguments.parse("--className", "foo");
        assertThat(arguments.getClassName()).hasValue("foo");
    }

    @Test
    void className_is_advanced_options() {
        final Arguments arguments = Arguments.parse("--className", "foo");
        assertThat(arguments.hasAdvancedOptions()).isTrue();
    }

    @Test
    void className_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-c"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("className must have a parameter.");
    }

    @Test
    void fullClassName_short() {
        final Arguments arguments = Arguments.parse("-f", "foo");
        assertThat(arguments.getFullClassName()).hasValue("foo");
    }

    @Test
    void fullClassName_long() {
        final Arguments arguments = Arguments.parse("--fullClassName", "foo");
        assertThat(arguments.getFullClassName()).hasValue("foo");
    }

    @Test
    void fullClassName_is_advanced_options() {
        final Arguments arguments = Arguments.parse("--fullClassName", "foo");
        assertThat(arguments.hasAdvancedOptions()).isTrue();
    }

    @Test
    void fullClassName_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-f"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("fullClassName must have a parameter.");
    }

    @Test
    void sha1_short() {
        final Arguments arguments = Arguments.parse("-1", "foo");
        assertThat(arguments.getSha1()).hasValue("foo");
    }

    @Test
    void sha1_long() {
        final Arguments arguments = Arguments.parse("--sha1", "foo");
        assertThat(arguments.getSha1()).hasValue("foo");
    }

    @Test
    void sha1_is_advanced_options() {
        final Arguments arguments = Arguments.parse("--sha1", "foo");
        assertThat(arguments.hasAdvancedOptions()).isTrue();
    }

    @Test
    void sha1_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-1"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("sha1 must have a parameter.");
    }

    @Test
    void rows_short() {
        final Arguments arguments = Arguments.parse("-r", "10");
        assertThat(arguments.getRows()).hasValue(10);
    }

    @Test
    void rows_long() {
        final Arguments arguments = Arguments.parse("--rows", "20");
        assertThat(arguments.getRows()).hasValue(20);
    }

    @Test
    void rows_if_empty() {
        final Arguments arguments = Arguments.parse("-g", "foo");
        assertThat(arguments.getRows()).isEmpty();
    }


    @Test
    void rows_is_not_advanced_options() {
        final Arguments arguments = Arguments.parse("--rows", "10");
        assertThat(arguments.hasAdvancedOptions()).isFalse();
    }

    @Test
    void rows_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-r"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("rows must have a parameter.");
    }

    @Test
    void rows_is_not_number() {
        assertThatThrownBy(() -> Arguments.parse("-r", "a"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("rows must be integer.");
    }

    @Test
    void httpProxy() {
        final Arguments arguments = Arguments.parse("--httpProxy", "foo:1234");
        assertThat(arguments.getHttpProxy()).hasValue("foo:1234");
    }

    @Test
    void httpProxy_only_get_host() {
        final Arguments arguments = Arguments.parse("--httpProxy", "foo:1234");
        assertThat(arguments.getProxyHost()).hasValue("foo");
    }

    @Test
    void httpProxy_only_get_port() {
        final Arguments arguments = Arguments.parse("--httpProxy", "foo:1234");
        assertThat(arguments.getProxyPort()).hasValue(1234);
    }

    @Test
    void proxyPort_empty_if_not_set_httpProxy() {
        final Arguments arguments = Arguments.parse("-g", "foo");
        assertThat(arguments.getProxyPort()).isEmpty();
    }

    @Test
    void httpProxy_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("--httpProxy"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("httpProxy must have a parameter.");
    }

    @Test
    void httpProxy_no_port() {
        assertThatThrownBy(() -> Arguments.parse("--httpProxy", "foo"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("httpProxy must be \"host:port\".");
    }

    @Test
    void httpProxy_port_is_not_integer() {
        assertThatThrownBy(() -> Arguments.parse("--httpProxy", "foo:abc"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("httpProxy port must be integer.");
    }

    @Test
    void proxyUser() {
        final Arguments arguments = Arguments.parse("--proxyUser", "foo");
        assertThat(arguments.getProxyUser()).hasValue("foo");
    }

    @Test
    void proxyUser_is_not_advanced_options() {
        final Arguments arguments = Arguments.parse("--proxyUser", "foo");
        assertThat(arguments.hasAdvancedOptions()).isFalse();
    }

    @Test
    void proxyUser_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("--proxyUser"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("proxyUser must have a parameter.");
    }

    @Test
    void proxyPass() {
        final Arguments arguments = Arguments.parse("--proxyPass", "foo");
        assertThat(arguments.getProxyPass()).hasValue("foo");
    }

    @Test
    void proxyPass_is_not_advanced_options() {
        final Arguments arguments = Arguments.parse("--proxyPass", "foo");
        assertThat(arguments.hasAdvancedOptions()).isFalse();
    }

    @Test
    void proxyPass_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("--proxyPass"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("proxyPass must have a parameter.");
    }

    @Test
    void query() {
        final Arguments arguments = Arguments.parse("foo");
        assertThat(arguments.getQuery()).hasValue("foo");
    }

    @Test
    void query_with_row() {
        final Arguments arguments = Arguments.parse("-r", "20", "foo");
        assertThat(arguments.getRows()).hasValue(20);
        assertThat(arguments.getQuery()).hasValue("foo");
    }

    @Test
    void query_is_empty_if_only_options() {
        final Arguments arguments = Arguments.parse("-g", "foo", "-a", "bar");
        assertThat(arguments.getQuery()).isEmpty();
        assertThat(arguments.getGroupId()).hasValue("foo");
        assertThat(arguments.getArtifactId()).hasValue("bar");
    }

    @Test
    void debug_enable() {
        final Arguments arguments = Arguments.parse("--debug");
        assertThat(arguments.isDebug()).isTrue();
    }

    @Test
    void start_short() {
        final Arguments arguments = Arguments.parse("-s", "1");
        assertThat(arguments.getStart()).hasValue(1);
    }

    @Test
    void start_long() {
        final Arguments arguments = Arguments.parse("--start", "1");
        assertThat(arguments.getStart()).hasValue(1);
    }

    @Test
    void start_if_empty() {
        final Arguments arguments = Arguments.parse("-g", "foo");
        assertThat(arguments.getStart()).isEmpty();
    }

    @Test
    void start_is_not_advanced_options() {
        final Arguments arguments = Arguments.parse("--start", "10");
        assertThat(arguments.hasAdvancedOptions()).isFalse();
    }

    @Test
    void start_no_parameter() {
        assertThatThrownBy(() -> Arguments.parse("-s"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("start must have a parameter.");
    }

    @Test
    void start_is_not_number() {
        assertThatThrownBy(() -> Arguments.parse("-s", "a"))
                .isInstanceOf(IllegalCommandLineArgsException.class)
                .hasMessage("start must be integer.");
    }

    @Test
    void all_enable() {
        final Arguments arguments = Arguments.parse("--all");
        assertThat(arguments.isAllVersions()).isTrue();
    }
}