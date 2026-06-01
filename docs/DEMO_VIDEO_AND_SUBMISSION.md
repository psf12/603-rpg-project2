# Demo Video Script + Submission Checklist

## Demo video (max 5 minutes)
The video must cover three things. A suggested running order:

1. **How GenAI was used (~1 min)**
   - State that GenAI (Claude) assisted with implementation, while the team
     designed the game, the GUI, and supplied the images and made the key
     engineering decisions (see AI Usage Log).
   - Show the GitHub commit history scrolling, to demonstrate step-by-step
     evolution rather than one dump.

2. **System functionality (~2.5 min)**
   - Launch the GUI from NetBeans (Run).
   - Main menu → New Game. Show the large scene image (~70%) changing per event.
   - Play a few events: a Yes/No choice, picking up an item, a battle. Point out
     the live HP/DMG bar updating (Observer).
   - Die / finish, enter a name, show the score saved.
   - Open the Leaderboard screen — show the score persisted in the database.
   - (Optional) close and reopen the app, show the score is still there
     (database persistence).

3. **Code structure (~1.5 min)** — high level, no line-by-line:
   - Show the package layers: `ui` / `engine` / `model` / `dao`.
   - Name the design patterns (Strategy, Singleton, DAO, Factory, Observer).
   - Show the `dao` package (Derby + DAO) and the `images/` folder (drop-in PNGs).
   - Run the JUnit tests (Test) and show all 13 passing.

> If the video is missing, the team may be asked to attend a live review.

## Final submission checklist
- [ ] Add your real PNG images to `603_Assignment/images/` (names in its README).
- [ ] Open the project in NetBeans, confirm it **compiles and runs** with no
      manual configuration (no manually adding libraries, no manual DB setup).
- [ ] Run the tests in NetBeans — confirm all pass.
- [ ] Fill in the **critical reflection** and **contribution %** in the report
      (`docs/PROJECT2_REPORT.md`) — in your own words, no AI text in the reflection.
- [ ] Export the report to Word/PDF; include the AI Usage Log in the appendix.
- [ ] Record the demo video (≤ 5 min).
- [ ] Ensure the **`.git` folder is included** in the project folder.
- [ ] Zip the whole `603_Assignment` project folder + report + video.
- [ ] **Name the ZIP:** `P67_23207490_23207340.zip`
- [ ] Submit via Canvas by Week 13, Friday 11:59 pm.

## Notes
- The build uses **JDK 25**. The project is a NetBeans **Maven** project; Maven
  downloads Derby + JUnit automatically on first build (internet needed once).
- The Derby database folder (`rpgdb/`) and `derby.log` are generated at runtime
  and are intentionally not committed (the database recreates itself on run).
