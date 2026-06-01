package com.mycompany.rpg.model;

import java.time.LocalDateTime;

/**
 * Immutable value object representing one leaderboard row.
 *
 * Two constructors: a short one for a brand-new score about to be inserted
 * (the database assigns the id and timestamp), and a full one used when reading
 * existing rows back out of the database.
 *
 * @author balla
 */
public class ScoreEntry {

    private final int id;
    private final String playerName;
    private final int score;
    private final LocalDateTime achievedAt;

    /** New score to be persisted (id + timestamp filled in by the database). */
    public ScoreEntry(String playerName, int score) {
        this(0, playerName, score, null);
    }

    /** Full record as read back from the database. */
    public ScoreEntry(int id, String playerName, int score, LocalDateTime achievedAt) {
        this.id = id;
        this.playerName = playerName;
        this.score = score;
        this.achievedAt = achievedAt;
    }

    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getAchievedAt() {
        return achievedAt;
    }
}
