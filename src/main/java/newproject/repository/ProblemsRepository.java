package newproject.repository;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import newproject.CassandraConnector;
import newproject.domain.Problems;
import org.springframework.stereotype.Component;

/**
 * Created By Alireza Dolatabadi
 * Date: 7/4/2022
 * Time: 9:35 AM
 */

@Component
public class ProblemsRepository {

    private static final String TABLE_NAME = "problems";

    private static final String TABLE_NAME_BY_TITLE = TABLE_NAME + "ByName";

    private final CassandraConnector cassandraConnector;

    public ProblemsRepository(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }

    /**
     * Creates the Problems table.
     */
    public void createTable() {
        Session session = cassandraConnector.getSession();
        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + "num text PRIMARY KEY, " + "name text," + "rating text," + "tags set<text>);";
        session.execute(query);
    }

    /**
     * Creates the Problems table.
     */
    public void createTableProblemsByName() {
        Session session = cassandraConnector.getSession();
        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BY_TITLE + "(" + "num text, " + "name text," + "PRIMARY KEY (name, num));";
        session.execute(query);
    }

    /**
     * Alters the table Problems and adds an extra column.
     */
    public void alterTableProblems(String columnName, String columnType) {
        Session session = cassandraConnector.getSession();

        final String query = "ALTER TABLE " + TABLE_NAME + " ADD " + columnName + " " + columnType + ";";
        session.execute(query);
    }

    /**
     * Insert a row in the table Problems.
     *
     * @param problems
     */
    public void insertProblem(Problems problems) {
        Session session = cassandraConnector.getSession();
        final String[] items = {"["};
        problems.getTags().forEach(c -> {
                items[0] = items[0].concat( "'"+c+"'" + ",");
        });
        items[0] = items[0].concat("]");
        final String query = "INSERT INTO " + TABLE_NAME + "(num, name, rating, tags) " + "VALUES ('" + problems.getNum() + "', '" + problems.getName() + "', '" + problems.getRating() + "', '" +  items[0] +
                "');";
        session.execute(query);
    }

    /**
     * Insert a row in the table ProblemsByName.
     *
     * @param problems
     */
    public void insertProblemByName(Problems problems) {
        Session session = cassandraConnector.getSession();

        final String query = "INSERT INTO " + TABLE_NAME_BY_TITLE + "(num, name) " + "VALUES (" + problems.getNum() + ", '" + problems.getName() + "');";
        session.execute(query);
    }

    /**
     * Select problems by num.
     *
     * @return
     */
    public Problems selectByName(String name) {
        Session session = cassandraConnector.getSession();

        final String query = "SELECT * FROM " + TABLE_NAME_BY_TITLE + " WHERE name = '" + name + "';";

        ResultSet rs = session.execute(query);

        List<Problems> Problems = new ArrayList<Problems>();

        for (Row r : rs) {
            Problems s = new Problems(r.getString("num"), null, r.getString("name"), null);
            Problems.add(s);
        }

        return Problems.get(0);
    }

    /**
     * Select all Problems from Problems
     *
     * @return
     */
    public List<Problems> selectAll() {
        Session session = cassandraConnector.getSession();

        final String query = "SELECT * FROM " + TABLE_NAME;
        ResultSet rs = session.execute(query);

        List<Problems> Problems = new ArrayList<Problems>();

        for (Row r : rs) {
            Problems problems = new Problems(r.getString("num"), r.getList("tags", String.class), r.getString("rating"), r.getString("name"));
            Problems.add(problems);
        }
        return Problems;
    }

    /**
     * Select all Problems from ProblemsByName
     *
     * @return
     */
    public List<Problems> selectAllProblemByName() {
        Session session = cassandraConnector.getSession();

        final String query = "SELECT * FROM " + TABLE_NAME_BY_TITLE;
        ResultSet rs = session.execute(query);

        List<Problems> Problems = new ArrayList<Problems>();

        for (Row r : rs) {
            Problems problems = new Problems(r.getString("num"), r.getList("tags", String.class), r.getString("rating"), r.getString("name"));
            Problems.add(problems);
        }
        return Problems;
    }

    /**
     * Delete a problems by name.
     */
    public void deleteProblemByName(String name) {
        Session session = cassandraConnector.getSession();

        final String query = "DELETE FROM " + TABLE_NAME_BY_TITLE + " WHERE name = '" + name + "';";
        session.execute(query);
    }

    /**
     * Delete table.
     *
     * @param tableName the name of the table to delete.
     */
    public void deleteTable(String tableName) {
        Session session = cassandraConnector.getSession();

        final String query = "DROP TABLE IF EXISTS " + tableName;
        session.execute(query);
    }
}
