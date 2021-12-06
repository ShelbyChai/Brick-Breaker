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


# Small Refactoring Activities
- Added Maven build files.
- Renamed variable, parameter and remove unused code.
- Created a Movable interface for player and ball class.
- Created a Ball Factory.
- Splitted DebugConsole, GameBoard, HomeMenu and PauseMenu into MVC.

# Additions
