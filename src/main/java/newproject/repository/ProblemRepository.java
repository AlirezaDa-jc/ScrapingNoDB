package newproject.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import newproject.CassandraConnector;
import newproject.domain.ProblemSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Alireza Dolatabadi
 * Date: 7/4/2022
 * Time: 9:35 AM
 */

@Component
public class ProblemRepository {

    private static final String TABLE_NAME = "problemSet";

    private static final String TABLE_NAME_BY_TITLE = TABLE_NAME + "ByName";

    private final CassandraConnector cassandraConnector;

    public ProblemRepository(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }

    /**
     * Creates the problemSet table.
     */
    public void createTable() {
        Session session = cassandraConnector.getSession();
        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + "num text PRIMARY KEY, " + "title text," + "acceptance text," + "url text," + "difficulty text);";
        session.execute(query);
    }

    /**
     * Creates the problemSet table.
     */
    public void createTableproblemSetByName() {
        Session session = cassandraConnector.getSession();
        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BY_TITLE + "(" + "num text, " + "title text," + "PRIMARY KEY (title, num));";
        session.execute(query);
    }

    /**
     * Alters the table problemSet and adds an extra column.
     */
    public void alterTableproblemSet(String columnName, String columnType) {
        Session session = cassandraConnector.getSession();

        final String query = "ALTER TABLE " + TABLE_NAME + " ADD " + columnName + " " + columnType + ";";
        session.execute(query);
    }

    /**
     * Insert a row in the table problemSet.
     *
     * @param problemSet
     */
    public void insertbook(ProblemSet problemSet) {
        Session session = cassandraConnector.getSession();

        final String query = "INSERT INTO " + TABLE_NAME + "(num, title, acceptance, url,difficulty) " + "VALUES ('" + problemSet.getNum() + "', '" + problemSet.getTitle() + "', '" + problemSet.getAcceptance() + "', '" + problemSet.getUrl() + "', '" +  problemSet.getDifficulty() +
                "');";
        session.execute(query);
    }

    /**
     * Select problemSet by num.
     *
     * @return
     */
    public ProblemSet selectByName(String title) {
        Session session = cassandraConnector.getSession();

        final String query = "SELECT * FROM " + TABLE_NAME_BY_TITLE + " WHERE title = '" + title + "';";

        ResultSet rs = session.execute(query);

        List<ProblemSet> problemSet = new ArrayList<ProblemSet>();

        for (Row r : rs) {
            ProblemSet s = new ProblemSet(r.getString("num"), r.getString("title"), r.getString("url"),r.getString("acceptance"), r.getString("difficulty"));
            problemSet.add(s);
        }

        return problemSet.get(0);
    }

    /**
     * Select all problemSet from problemSet
     *
     * @return
     */
    public List<ProblemSet> selectAll() {
        Session session = cassandraConnector.getSession();

        final String query = "SELECT * FROM " + TABLE_NAME;
        ResultSet rs = session.execute(query);

        List<ProblemSet> problemSet = new ArrayList<ProblemSet>();

        for (Row r : rs) {
            ProblemSet s = new ProblemSet(r.getString("num"), r.getString("title"), r.getString("url"),r.getString("acceptance"), r.getString("difficulty"));
            problemSet.add(s);
        }
        return problemSet;
    }

    /**
     * Select all problemSet from problemSetByName
     *
     * @return
     */
    public List<ProblemSet> selectAllBookByName() {
        Session session = cassandraConnector.getSession();

        final String query = "SELECT * FROM " + TABLE_NAME_BY_TITLE;
        ResultSet rs = session.execute(query);

        List<ProblemSet> problemSet = new ArrayList<ProblemSet>();

        for (Row r : rs) {
            ProblemSet s = new ProblemSet(r.getString("num"), r.getString("title"), r.getString("url"),r.getString("acceptance"), r.getString("difficulty"));
            problemSet.add(s);
        }
        return problemSet;
    }

    /**
     * Delete a problemSet by title.
     */
    public void deletebookByName(String title) {
        Session session = cassandraConnector.getSession();

        final String query = "DELETE FROM " + TABLE_NAME_BY_TITLE + " WHERE title = '" + title + "';";
        session.execute(query);
    }

    /**
     * Delete table.
     *
     * @param tableName the title of the table to delete.
     */
    public void deleteTable(String tableName) {
        Session session = cassandraConnector.getSession();

        final String query = "DROP TABLE IF EXISTS " + tableName;
        session.execute(query);
    }
}
