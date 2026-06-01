package com.mycompany.rpg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton that owns the embedded Apache Derby database connection.
 *
 * Using a single shared connection for this small desktop game keeps the
 * database lifecycle in one place (Singleton pattern). The database is created
 * automatically on first use ({@code create=true}), so the project runs in
 * NetBeans with no manual Derby setup.
 *
 * The JDBC URL can be overridden with the system property {@code rpg.db.url}
 * (used by the JUnit tests to target a throwaway in-memory database).
 *
 * @author balla
 */
public final class Database {

    private static final String DEFAULT_URL = "jdbc:derby:rpgdb;create=true";

    private static Database instance;

    private final Connection connection;

    private Database() {
        try {
            // Explicit load of the embedded driver (also auto-registers via the
            // JDBC service loader; loading twice is harmless and self-documenting).
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = System.getProperty("rpg.db.url", DEFAULT_URL);
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to initialise embedded Derby database", e);
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the connection and shuts the embedded Derby engine down cleanly so
     * it releases its file lock. Derby signals a successful shutdown by throwing
     * a SQLException (state XJ015 / 08006), which is expected and ignored.
     */
    public synchronized void shutdown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ignored) {
            // best effort
        }
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException expected) {
            // XJ015 (engine) / 08006 (single db) = normal shutdown signal
        }
        instance = null;
    }
}
