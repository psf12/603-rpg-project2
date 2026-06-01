# Project 2 Report — RPG Game (GUI + Database Extension)

**Course:** COMP603 / ENSE600 — Program Design & Construction / Software Construction
**Group:** 67  **Students:** 23207340, 23207490
**GitHub repository:** https://github.com/psf12/603-rpg-project2

> This report covers **Project 2 only**. Project 1's requirements (Component 1.2)
> and program-design critical reflection (Component 1.3) are in the Project 1
> report. Sections tagged **[TEAM — write in your own words]** must be completed
> by the team (the critical evaluation of AI output should be student-authored).

---

## Account credentials
None required. The game uses an **embedded** Apache Derby database that is created
automatically on first run (`create=true`). There is no login, no server, and no
manual database setup.

## How to build and run
- NetBeans **Maven** project, **JDK 25**.
- Open the project in NetBeans and press **Run** — this launches the GUI. Maven
  downloads the Apache Derby and JUnit libraries automatically on the first build
  (internet required once).
- The original text-based version can still be run with the program argument
  `--console`.
- Tests: right-click the project → **Test** (or `mvn test`).

---

## 1. Overview — a significant extension of Project 1
Project 1 was a text-based (CUI) role-playing game: the player moves through random
events (Chest, Battle, Cleanse, Cursed Statue), makes Yes/No choices, collects
items, fights enemies, and records a high score. Project 2 keeps the same game and
rules but extends it into a full **Java Swing GUI application** backed by an
**embedded Apache Derby database**, with a **JUnit** test layer and a layered,
design-pattern-based architecture.

New in Project 2:
- A graphical, **image-forward** user interface (replaces console text I/O).
- An **embedded Derby database + DAO layer** providing a persistent high-score
  leaderboard (replaces the old `highscore.txt` file).
- A **layered architecture** (`ui` / `engine` / `model` / `dao`) with clear
  separation of concerns.
- **Design patterns:** Strategy, Singleton, DAO, Factory, Observer (and a Facade).
- A **JUnit 4 test suite** (13 tests).
- **Git/GitHub** version control with incremental, meaningful commits.

---

## Component 2.2 — Graphical User Interface (20%)

**Design (team-designed — see AI Usage Log).** The GUI uses an image-forward
layout: a large scene image fills roughly the top **70%** of the window, with the
narrative text log and the input controls (Yes / No, Continue, and a name field)
beneath it. A thin stats bar at the top shows live HP/DMG. The application has
three screens managed by a `CardLayout`:

1. **Main Menu** — New Game, Leaderboard, Quit.
2. **Game** — scene image + text log + context-sensitive buttons.
3. **Leaderboard** — top scores in a table, read live from the database.

How the GUI meets the rubric:
- **Clear, professional, well-structured layout** — consistent three-screen flow;
  the dominant scene image gives each event a distinct identity.
- **Ease of use** — entirely button-driven; the player never types commands, and
  only the controls valid for the current prompt are enabled.
- **Consistency between GUI actions and business logic** — the GUI never contains
  game rules; it talks to the unchanged game engine through the `GameView`
  interface, so on-screen actions map exactly to the underlying logic.
- **Scene images** — supplied as pre-named PNG files in the `images/` folder (one
  per event); art can be added or changed without touching code.

**Critical evaluation and refinement of AI-generated UI.** **[TEAM — write in your
own words.]** Points you can draw on (these are factual records of what happened):
- The team designed the image-forward concept (≈70% image, per-event PNG folder,
  three-screen flow); the AI implemented it in Swing to that specification.
- The first implementation was reviewed and refined so the controls only enable
  when relevant (preventing invalid clicks) and the game runs on a background
  thread so the window stays responsive while waiting for the player's input.

---

## Component 2.3 — Database (20%)

- **Engine:** Apache Derby, **embedded** mode.
- **Access:** **JDBC**, behind a **DAO** layer.
- **Classes:** `Database` (Singleton owning the embedded connection),
  `ScoreDAO` (interface), `DerbyScoreDAO` (JDBC implementation).
- **Schema (auto-created on first run):**
  `SCORES(ID INTEGER identity PK, PLAYER_NAME VARCHAR(50), SCORE INTEGER, ACHIEVED_AT TIMESTAMP)`.
- **Essential to the system:** the database powers the high-score shown at the
  start of a run and the top-N leaderboard shown at game over and on the
  Leaderboard screen. Scores persist across separate runs of the program.
- **Review of AI-generated database code.** The DAO code was reviewed and verified
  with JUnit tests against a throwaway in-memory Derby database, and cross-run
  persistence was confirmed manually. **[TEAM — add any corrections you made, in
  your own words.]**

---

## Component 2.4 — Software Functionality, Design, Implementation & Testing (40%)

### 2.4.1 Functionality & Usability
- **No manual configuration** — the database and table are created automatically;
  no Derby service to start, no libraries to add by hand.
- **Functional complexity** — random event system, battles with difficulty tiers
  and an escape mechanic, items with stacking stat bonuses and effects, a cleanse
  mechanic, and a persistent leaderboard.
- **Logical flow** between the menu, game, and leaderboard screens.
- **Correct persistence** — scores are saved and retrieved reliably (verified by
  tests and across runs).
- **Graceful handling of problems** — input is button-driven so invalid entries
  are largely prevented; a database start-up failure shows a clear dialog instead
  of crashing; long names are capped to fit the database column.
- **OOP fully reflected** — inheritance (`NPC extends Player`, the `Event`
  hierarchy), polymorphism (events and effects), encapsulation, and abstraction
  via interfaces (`GameView`, `ScoreDAO`, `Effects`, `PlayerListener`).

### 2.4.2 Architecture
**Layered architecture (separation of concerns):**

| Layer | Package | Responsibility |
|-------|---------|----------------|
| Presentation | `ui` | `GameView`, `ConsoleView`, `SwingView`, `MainFrame`, `ImagePanel`, `GameIO` |
| Game logic | `engine` | `GameEngine`, `Event` (+ subclasses), `EventHandler`, `BattleHandler` |
| Domain model | `model` | `Player`, `NPC`, `Item`, `ItemType`, `ItemManager`, `ScoreEntry`, factories |
| Data access | `dao` | `Database`, `ScoreDAO`, `DerbyScoreDAO` |

The game logic depends on the presentation only through the `GameView` interface
and on persistence only through the `ScoreDAO` interface — so the GUI, business
logic, and data access are fully decoupled (the same engine drives both the
console and the GUI without modification).

**Design patterns applied:**

| Pattern | Where | Purpose |
|---------|-------|---------|
| Strategy | `GameView` (`ConsoleView` / `SwingView`) | Swap presentation without changing game logic |
| Singleton | `Database` | One shared embedded-Derby connection |
| DAO | `ScoreDAO` / `DerbyScoreDAO` | Isolate data access behind an interface |
| Factory | `NPCFactory`, `ItemFactory` | Build a fresh NPC/Item instance each call |
| Observer | `PlayerListener` → `SwingView` | Live HP/DMG display updates on stat change |
| Facade | `GameIO` | Single entry point routing I/O to the active view |

**Identifying and correcting poor AI design.** **[TEAM — write in your own words.]**
Factual records you can draw on:
- While applying the Factory pattern, a Project 1 **shared-state bug** was found:
  enemies and items were single shared static instances, so a defeated enemy kept
  its lost HP into the next encounter. The factories now create a fresh object per
  call, fixing this.
- The AI initially described shared item instances as a bug "with inconsistent
  bonuses." The team identified that **item stacking is intended behaviour** and
  corrected that characterisation; the factory change preserves stacking.

### 2.4.3 Version Control (Git / GitHub)
- Repository: https://github.com/psf12/603-rpg-project2 (the `.git` folder is
  included in the submitted project).
- Multiple meaningful commits show the system evolving step by step: baseline →
  Maven/tooling → layered refactor → I/O abstraction → database + DAO → Swing GUI
  → design patterns → JUnit tests → robustness polish → documentation. This is a
  genuine development history, not a single final dump.

### 2.4.4 Testing (JUnit 4)
13 meaningful tests, all passing (`mvn test`):
- `DerbyScoreDAOTest` (5) — **database operations** against a throwaway in-memory
  Derby database: insert + retrieve, high-score maximum, empty database = 0,
  ordering, and result limit.
- `PlayerTest` (5) — **business logic**: damage, healing, alive/dead condition,
  and the Observer notification.
- `FactoryTest` (3) — **business logic**: factories produce independent instances
  (verifying the shared-state fix) and duplicate items stack their stat bonus.

---

## Appendix — AI Usage Log (Component 2.5)

**GenAI tool used:** Anthropic Claude (Claude Code, Opus model).

### Types of tasks GenAI assisted with
- Reading and explaining the existing Project 1 codebase.
- Setting up Git/GitHub and the Maven build (Apache Derby + JUnit dependencies).
- Refactoring the flat packages into a layered architecture.
- Implementing the `GameView` presentation abstraction and the `GameEngine`.
- Implementing the embedded Derby database and DAO layer.
- Writing the Swing GUI **code** to match the team's design.
- Applying design patterns (Strategy, Singleton, DAO, Factory, Observer).
- Writing the JUnit 4 test suite.
- Robustness / error-handling improvements.

### Main prompts used (paraphrased)
1. "Read the assignment, focus on Project 2, and record the required steps."
2. "Read through the Project 1 code and explain how it works."
3. "Set up Git and GitHub and the Maven project (we are new to Git)."
4. "Reorganise the project into a clean layered architecture."
5. "Add an embedded Apache Derby database with a DAO layer for the high scores."
6. "Build the GUI so a large portion (~70%) of the screen is a scene image, with
   each event's image just a pre-named PNG in a folder." *(team GUI design)*
7. "Apply appropriate design patterns; the game has no official name."
8. "Item stacking is intended behaviour." *(team correcting the AI)*
9. "Add a JUnit test suite covering the database and the business logic."
10. "Add robustness polish and write the documentation."

### Parts that were mainly HUMAN-designed
- **The game concept, story, events, and rules** — designed by the team in
  Project 1 and carried into Project 2.
- **All images / artwork** — created and supplied by the team; the AI wrote only
  the code that loads and displays them.
- **The GUI design** — the image-forward layout (~70% scene image), the per-event
  PNG-folder approach, and the menu → game → leaderboard screen flow were designed
  by the team; the AI implemented this design in Swing.
- **Key engineering decisions** — choice of Swing, JDBC, what the database stores
  (a leaderboard), and which design patterns to apply and where, were decided by
  the team; the AI implemented them.
- **Review, correction, and testing** of all AI-generated code.

### Demonstration video
A demonstration video (≤ 5 minutes) accompanies this submission, covering (1) how
GenAI was used, (2) the system functionality, and (3) the overall code structure.

---

## Contribution report  **[TEAM — fill in]**
- **23207340** — _contribution description_ — _%_
- **23207490** — _contribution description_ — _%_

> (If a member contributes less than 40%, their mark may be capped; ensure both
> percentages reflect the real split.)
