/*
 * The author disclaims copyright to this source code. In place of
 * a legal notice, here is a blessing:
 *    May you do good and not evil.
 *    May you find forgiveness for yourself and forgive others.
 *    May you share freely, never taking more than you give.
 */
package local.poc.resin;

import org.eclipse.jetty.server.session.SessionDataStore;
import org.eclipse.jetty.server.session.SessionDataStoreFactory;
import org.eclipse.jetty.server.session.SessionHandler;

/**
 *
 * @author Andrey Lebedenko (andrey.lebedenko@gmail.com)
 */
public class JwtSessionDataStoreFactory implements SessionDataStoreFactory {
    private final int maxDataStoreSizeInBytes;

    public JwtSessionDataStoreFactory(int maxDataStoreSizeInBytes) {
        super();
        this.maxDataStoreSizeInBytes = maxDataStoreSizeInBytes;
    }

    @Override
    public SessionDataStore getSessionDataStore(final SessionHandler handler) throws Exception {
        return new JwtSessionDataStore(handler);
    }

    public int getMaxDataStoreSizeInBytes() {
        return maxDataStoreSizeInBytes;
    }
}

