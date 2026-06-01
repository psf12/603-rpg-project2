package com.mycompany.rpg.dao;

import com.mycompany.rpg.model.ScoreEntry;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of {@link ScoreDAO} backed by Apache Derby.
 *
 * The constructor takes the {@link Connection} to use, which keeps it decoupled
 * from the {@link Database} singleton and lets tests inject an in-memory
 * connection. The SCORES table is created automatically if it does not yet
 * exist, so no manual schema setup is required.
 *
 * @author balla
 */
public class DerbyScoreDAO implements ScoreDAO {

    private final Connection connection;

    public DerbyScoreDAO(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    /** Create the SCORES table on first use (idempotent). */
    private void ensureTable() {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            // Derby stores unquoted identifiers in upper case.
            try (ResultSet rs = meta.getTables(null, null, "SCORES", null)) {
                if (rs.next()) {
                    return; // already exists
                }
            }
            try (Statement st = connection.createStatement()) {
                st.executeUpdate(
                    "CREATE TABLE SCORES ("
                  + "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                  + "PLAYER_NAME VARCHAR(50) NOT NULL, "
                  + "SCORE INTEGER NOT NULL, "
                  + "ACHIEVED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                  + "PRIMARY KEY (ID))");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to ensure SCORES table exists", e);
        }
    }

    @Override
    public void addScore(ScoreEntry entry) {
        String sql = "INSERT INTO SCORES (PLAYER_NAME, SCORE) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entry.getPlayerName());
            ps.setInt(2, entry.getScore());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add score", e);
        }
    }

    @Override
    public int getHighScore() {
        String sql = "SELECT MAX(SCORE) FROM SCORES";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                int max = rs.getInt(1);
                return rs.wasNull() ? 0 : max;
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read high score", e);
        }
    }

    @Override
    public List<ScoreEntry> getTopScores(int limit) {
        if (limit < 1) {
            limit = 1;
        }
        // limit is an internal int (not user input), safe to inline; Derby does
        // not accept a bind parameter in FETCH FIRST.
        String sql = "SELECT ID, PLAYER_NAME, SCORE, ACHIEVED_AT FROM SCORES "
                   + "ORDER BY SCORE DESC, ACHIEVED_AT ASC "
                   + "FETCH FIRST " + limit + " ROWS ONLY";
        List<ScoreEntry> result = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new ScoreEntry(
                    rs.getInt("ID"),
                    rs.getString("PLAYER_NAME"),
                    rs.getInt("SCORE"),
                    rs.getTimestamp("ACHIEVED_AT").toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read top scores", e);
        }
        return result;
    }
}
