package com.mycompany.rpg.dao;

import com.mycompany.rpg.model.ScoreEntry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Database-operation tests for {@link DerbyScoreDAO} (Component 2.4.4).
 *
 * Each test runs against a fresh throwaway in-memory Derby database, so the
 * tests are isolated, repeatable, and leave no files behind.
 *
 * @author balla
 */
public class DerbyScoreDAOTest {

    private static final String URL = "jdbc:derby:memory:rpgTestDb;create=true";

    private Connection connection;
    private ScoreDAO dao;

    @BeforeClass
    public static void loadDriver() throws ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    }

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(URL);
        dao = new DerbyScoreDAO(connection); // auto-creates the SCORES table
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
        // Drop the in-memory database so the next test starts empty.
        try {
            DriverManager.getConnection("jdbc:derby:memory:rpgTestDb;drop=true");
        } catch (SQLException expected) {
            // Derby always throws on a successful drop (state 08006)
        }
    }

    @Test
    public void addedScoreCanBeRetrieved() {
        dao.addScore(new ScoreEntry("Alice", 42));

        List<ScoreEntry> top = dao.getTopScores(10);
        assertEquals(1, top.size());
        assertEquals("Alice", top.get(0).getPlayerName());
        assertEquals(42, top.get(0).getScore());
    }

    @Test
    public void highScoreReturnsTheMaximum() {
        dao.addScore(new ScoreEntry("A", 10));
        dao.addScore(new ScoreEntry("B", 55));
        dao.addScore(new ScoreEntry("C", 30));

        assertEquals(55, dao.getHighScore());
    }

    @Test
    public void highScoreIsZeroWhenNoScoresExist() {
        assertEquals(0, dao.getHighScore());
    }

    @Test
    public void topScoresAreOrderedByScoreDescending() {
        dao.addScore(new ScoreEntry("A", 10));
        dao.addScore(new ScoreEntry("B", 90));
        dao.addScore(new ScoreEntry("C", 50));

        List<ScoreEntry> top = dao.getTopScores(10);
        assertEquals(90, top.get(0).getScore());
        assertEquals(50, top.get(1).getScore());
        assertEquals(10, top.get(2).getScore());
    }

    @Test
    public void topScoresRespectsTheRequestedLimit() {
        for (int i = 1; i <= 8; i++) {
            dao.addScore(new ScoreEntry("P" + i, i * 5));
        }

        assertEquals(3, dao.getTopScores(3).size());
    }
}
