package com.mycompany.rpg.dao;

import com.mycompany.rpg.model.ScoreEntry;
import java.util.List;

/**
 * Data-access contract for leaderboard scores (DAO pattern).
 *
 * Defining the data access behind an interface keeps the game logic independent
 * of the concrete persistence technology and makes it easy to substitute a test
 * double or a different implementation.
 *
 * @author balla
 */
public interface ScoreDAO {

    /** Persist a newly achieved score. */
    void addScore(ScoreEntry entry);

    /** @return the highest score recorded, or 0 if none exist yet. */
    int getHighScore();

    /** @return up to {@code limit} entries, highest score first. */
    List<ScoreEntry> getTopScores(int limit);
}
