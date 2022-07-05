package newproject;

import jnr.ffi.annotations.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import org.springframework.stereotype.Component;

/**
 * Created By Alireza Dolatabadi
 * Date: 7/4/2022
 * Time: 8:27 AM
 */

/**
 *
 * This is an implementation of a simple Java client.
 *
 */
@Component
public class CassandraConnector {
    private static final Logger LOG = LoggerFactory.getLogger(CassandraConnector.class);

    private Cluster cluster;

    private Session session;

    public void connect() {

        String node = "127.0.0.1";
        Integer port = 9042;

        Builder b = Cluster.builder().addContactPoint(node);

        if (port != null) {
            b.withPort(port);
        }
        cluster = b.build();

        Metadata metadata = cluster.getMetadata();
        LOG.info("Cluster name: " + metadata.getClusterName());

        for (Host host : metadata.getAllHosts()) {
            LOG.info("Datacenter: " + host.getDatacenter() + " Host: " + host.getAddress() + " Rack: " + host.getRack());
        }

        session = cluster.connect();
        System.err.println(session);
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }
}