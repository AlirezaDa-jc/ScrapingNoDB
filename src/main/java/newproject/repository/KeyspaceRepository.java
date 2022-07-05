package newproject.repository;

import com.datastax.driver.core.Session;
import newproject.CassandraConnector;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created By Alireza Dolatabadi
 * Date: 7/4/2022
 * Time: 9:33 AM
 */

@Component
public class KeyspaceRepository {
    private final CassandraConnector cassandraConnector;
    Session session;

    @Lazy
    public KeyspaceRepository(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }

    public void createKeyspace(String keyspaceName, String replicatioonStrategy, int numberOfReplicas) {
        session = cassandraConnector.getSession();
        final String query = "CREATE KEYSPACE IF NOT EXISTS " + keyspaceName + " WITH replication = {" + "'class':'" + replicatioonStrategy + "','replication_factor':" + numberOfReplicas + "};";
        session.execute(query);
    }

    public void useKeyspace(String keyspace) {
        session = cassandraConnector.getSession();
        session.execute("USE " + keyspace);
    }

    public void deleteKeyspace(String keyspaceName) {
        session = cassandraConnector.getSession();
        final String query = "DROP KEYSPACE " + keyspaceName;
        session.execute(query);
    }
}
