package local.poc.resin;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jersey.sessions.HttpSessionFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.session.CachingSessionDataStoreFactory;
import org.eclipse.jetty.server.session.SessionDataStoreFactory;
import org.eclipse.jetty.server.session.SessionHandler;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.quercus.servlet.QuercusServlet;
/*
 * The author disclaims copyright to this source code. In place of
 * a legal notice, here is a blessing:
 *    May you do good and not evil.
 *    May you find forgiveness for yourself and forgive others.
 *    May you share freely, never taking more than you give.
 */
/**
 *
 * @author Andrey Lebedenko (andrey.lebedenko@gmail.com)
 */
public class Main extends Application<AppConfig> {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    new Main().run(args);
  }

  @Override
  public void initialize(Bootstrap<AppConfig> bootstrap) {
    bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    // Specify the Session ID Manager
  }

  @Override
  public void run(AppConfig appConfig, Environment environment) throws Exception {
    environment.jersey().setUrlPattern("/api/*");
    environment.getApplicationContext().setAttribute("application", this);
    environment.getApplicationContext().addServlet(QuercusServlet.class, "/php/*");

    SessionDataStoreFactory sessionDataStoreFactory = new CachingSessionDataStoreFactory(); //new JwtSessionDataStoreFactory(4096); // max data size = 4k

    environment.jersey().register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(sessionDataStoreFactory).to(SessionDataStoreFactory.class);
      }
    });

    environment.jersey().register(HttpSessionFactory.class);
    environment.servlets().setSessionHandler(new SessionHandler());
  }

  // This method will be called from PHP via Resin (PHP-to-Java) library
  public String myMegaMethod(int i) {
    return "MegaResponse_" + i;
  }

}

