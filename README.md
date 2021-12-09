# Brick_Destroy
This report provides a summary on the major refactoring activities and additions made to the Brick Destroyer Game provided for the Software Maintenance Coursework.

# Major Refactoring Activities
- Created a PauseMenu class by extracting it out from GameBoard class.
- Created a Crack class by extracting it from abstract Brick class.
- Created a Levels class by extracting level related content out from Wall class.
- Created Brick Factory to replace the switch statement in Levels.
- Combined the makeSingleTypeLevel and makeChessboardLevel in Levels class.
- Completed the conversion of swing to Javafx.
- Added enum to replace Brick Impact direction.
- Completed the MVC pattern.
- Created a SceneManager class to handle different scene in the game.
- Created an Entity Inteface class for player, ball and brick class.


# Small Refactoring Activities
- Added Maven build files.
- Renamed variable, parameter and remove unused code.
- Created a Movable interface for player and ball class.
- Created a Ball Factory.
- Splitted DebugConsole, GameBoard, HomeMenu and PauseMenu into MVC.
- Removed method overloading by renaming the method in Crack class.

# Additions
- Created an Info Screen to display key press instructions to the player.
- Created a player name prompt pop-up box for the usage of leaderboard.
- Implemented permanent highscore function to the game by file handling.
- Created a LeaderBoard Screen that uses the high_score text file to display all time highscore along with the playername.
- Added 2 new Brick Type (Black Stone Brick and Haste Brick)
- Added 2 new Player Level.
- Created an alert box when the user didn't enter user name.
- Created a summary information pop-up box after user clearing each level.
- Added different score calculation for each brick type and additional score base on the number of ball left after completing each level.
- Added background image for info menu, home menu, scoreboard and icon for the game.
- Improve the overall design of each fxml.
