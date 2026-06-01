# Project 2 — AI Usage Log (Component 2.5)

**Course:** COMP603 / ENSE600 — Program Design & Construction / Software Construction
**Group:** 67  **Students:** 23207490, 23207340
**GenAI tool used:** Anthropic Claude (Claude Code, Opus model)

This log documents how Generative AI was used during Project 2, and — importantly —
which parts of the work were designed and decided by the team rather than the AI.

---

## 1. Types of tasks GenAI assisted with

- Reading and explaining the existing Project 1 (CUI) codebase.
- Setting up Git/GitHub and configuring the Maven build (adding the Apache Derby
  and JUnit 4 dependencies; fixing the project's main-class configuration).
- Refactoring the flat Project 1 packages into a layered architecture
  (`model` / `engine` / `ui` / `dao`).
- Implementing the `GameView` presentation abstraction and extracting the game
  loop out of `main()` into a reusable `GameEngine`.
- Implementing the embedded Apache Derby database and the DAO data-access layer.
- Writing the Swing GUI **code** to match the team's design (see Section 3).
- Applying design patterns where appropriate (Strategy, Singleton, DAO, Factory,
  Observer) and fixing a shared-state bug uncovered along the way.
- Writing the JUnit 4 test suite.
- Robustness / graceful error-handling improvements.

## 2. Main prompts used (paraphrased)

The team directed the work step by step. The main prompts were:

1. "Read the assignment PDF, focus on Project 2, and record the required steps."
2. "Read through the Project 1 code and explain how the program works."
3. "Set up Git and GitHub and the Maven project (we are new to Git)."
4. "Reorganise the project into a clean layered architecture."
5. "Add an embedded Apache Derby database with a DAO layer for the high scores."
6. "Build the GUI so that a large portion (~70%) of the screen is covered by an
   image, and set it up so each event's image is just a pre-named PNG dropped
   into a folder." *(team-provided GUI design — see Section 3)*
7. "Apply appropriate design patterns; the game has no official name."
8. "Item stacking is intended behaviour." *(team clarifying a game rule)*
9. "Add a JUnit test suite covering the database and the business logic."
10. "Add robustness polish and write the documentation."

The team reviewed, corrected, and tested the AI-generated code at each step rather
than accepting it blindly (for example, confirming the game still ran after each
refactor, and correcting the AI's description of the item-stacking behaviour).

## 3. Parts that were mainly HUMAN-designed

- **The game concept, story, events, and rules** — designed by the team in
  Project 1 and carried into Project 2 unchanged.
- **All images / artwork** — created and supplied by the team. The AI wrote no
  images; it only wrote the code that loads and displays them.
- **The GUI design** — the team designed the user interface: the image-forward
  layout with a large (~70%) scene image, the approach of using a folder of
  pre-named PNGs (one per event), and the overall screen flow (main menu →
  game screen → leaderboard). GenAI implemented this design in Swing.
- **Key engineering decisions** — the team chose the GUI toolkit (Swing), the
  database-access approach (JDBC), what the database stores (a high-score
  leaderboard), and which design patterns to apply and where. These decisions
  were made/approved by the team; the AI implemented them.
- **Review, correction, and testing** of all AI-generated code.

> The student-authored critical reflection (with no AI-generated text) is in the
> main body of the project report, as required.
