package local.poc.resin;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @ClassRule
    public static DropwizardAppRule<AppConfig> rule = new DropwizardAppRule<>(
            Main.class, resourceFilePath("config.yml"));

    @Before
    public void setUp() {

    }

    @Test
    public void canAccessPhpResource() {
        Client client = rule.client();
        Response actualResponse = client.target(String.format("http://localhost:%d/php/demo.php", rule.getLocalPort()))
                .request().buildGet().invoke();
        String entity = actualResponse.readEntity(String.class);
        assertThat(entity).startsWith("Session started: 1<br/>SessionID:");
        assertThat(entity).endsWith("<br/>Call to java: MegaResponse_1");
    }
}