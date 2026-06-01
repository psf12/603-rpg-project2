# Project 2 — Report (Draft)

**Course:** COMP603 / ENSE600  **Group:** 67  **Students:** 23207490, 23207340
**GitHub:** https://github.com/psf12/603-rpg-project2

> This is a working draft to paste into the final Word/PDF report. Sections
> marked **[WRITE YOURSELF — NO AI TEXT]** must be written by the team in your
> own words, as the rubric requires the critical reflection to be student-authored.
> The factual technical sections below can be edited freely.

---

## Account credentials
None required. The game uses an **embedded** Apache Derby database that is created
automatically on first run (`create=true`); there is no login, server, or manual
database setup.

## 1. Overview
Project 2 extends the Project 1 text-based (CUI) RPG into a Java **GUI** application
backed by an embedded **Apache Derby** database, with a **JUnit** test layer and a
layered, pattern-based architecture. The game concept and rules are unchanged: the
player moves through random events (Chest, Battle, Cleanse, Cursed Statue), makes
Yes/No choices, survives as long as possible, and records a high score.

## 2. Architecture (layered — separation of concerns)
The code is organised under `com.mycompany.rpg` into clear layers:

| Layer | Package | Responsibility |
|-------|---------|----------------|
| Presentation | `ui` | `GameView`, `ConsoleView`, `SwingView`, `MainFrame`, `ImagePanel`, `GameIO` |
| Game logic | `engine` | `GameEngine`, `Event` (+ subclasses), `EventHandler`, `BattleHandler` |
| Domain model | `model` | `Player`, `NPC`, `Item`, `ItemType`, `ItemManager`, `ScoreEntry`, factories |
| Data access | `dao` | `Database`, `ScoreDAO`, `DerbyScoreDAO` |

The game logic talks to the presentation only through the `GameView` interface and
to persistence only through the `ScoreDAO` interface, so the three concerns are
fully decoupled (the same engine drives both the console and the GUI unchanged).

## 3. Design patterns

| Pattern | Where | Purpose |
|---------|-------|---------|
| **Strategy** | `GameView` (`ConsoleView` / `SwingView`) | Swap presentation without touching game logic |
| **Singleton** | `Database` | One shared embedded-Derby connection |
| **DAO** | `ScoreDAO` / `DerbyScoreDAO` | Isolate data access behind an interface |
| **Factory** | `NPCFactory`, `ItemFactory` | Build fresh NPC/Item instances each call |
| **Observer** | `PlayerListener` → `SwingView` | Live HP/DMG display updates on stat change |
| **Facade** | `GameIO` | Single entry point routing I/O to the active view |

The Factory pattern also fixed a Project 1 bug: enemies and items were shared
static instances, so a defeated enemy kept its lost HP into the next encounter.
The factories now create a fresh object per call. (Item *stacking* — holding
multiple copies of the same curse/buff — is intended and is preserved.)

## 4. Database design
- **Engine:** Apache Derby, embedded mode, accessed via **JDBC**.
- **Schema:** a single table `SCORES(ID, PLAYER_NAME, SCORE, ACHIEVED_AT)`, created
  automatically on first run.
- **Access:** the `ScoreDAO` interface with a `DerbyScoreDAO` JDBC implementation
  (`addScore`, `getHighScore`, `getTopScores`). The `Database` singleton owns the
  connection and shuts Derby down cleanly on exit.
- The database is essential to the game: it powers the high-score check at the start
  and the top-N leaderboard shown at game over and on the Leaderboard screen.

## 5. Graphical user interface
**Designed by the team** (see AI Usage Log). Key design: an image-forward layout
where a large scene image fills roughly the top **70%** of the window, with the
narrative text and Yes/No / Continue / name-entry controls below it. Scene artwork
is supplied as pre-named PNG files in the `images/` folder (one per event), so art
can be added or changed without touching code. Screens: **main menu → game →
leaderboard** (`CardLayout`). The engine runs on a background thread so the GUI
stays responsive while the game waits for the player's button input.

## 6. Testing (JUnit 4)
13 tests, all passing (`mvn test`):
- `DerbyScoreDAOTest` (5) — database operations against a throwaway in-memory Derby
  database: insert+retrieve, high-score maximum, empty = 0, ordering, limit.
- `PlayerTest` (5) — business logic: damage, healing, alive/dead, observer.
- `FactoryTest` (3) — fresh-instance creation and item-stacking behaviour.

## 7. Version control
Developed on GitHub with multiple meaningful commits showing the evolution
(baseline → tooling → layered refactor → I/O abstraction → database → GUI →
patterns → tests → polish): https://github.com/psf12/603-rpg-project2

---

## 8. Critical reflection  **[WRITE YOURSELF — NO AI TEXT]**
Write, in your own words, about Project 2 (a few short paragraphs). Suggested points
to cover (do not copy these — use them as prompts):
- A design suggestion the AI made that you accepted, and why it was good.
- A place where the AI's output was wrong, over-engineered, or had to be corrected
  (e.g. it described item-sharing as a bug when item *stacking* is intended; the
  real bug was the shared-NPC state). How did you catch and fix it?
- Why the final layered/pattern-based design is more appropriate than the original.
- What you (the team) designed yourselves vs. what the AI implemented.

## 9. Contribution report  **[FILL IN YOURSELVES]**
- 23207490 — _your contribution_ — _%_
- 23207340 — _your contribution_ — _%_

## Appendix — AI Usage Log
See `docs/AI_USAGE_LOG.md` (paste into the report appendix).
