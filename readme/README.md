# Brick Breaker ![brick-icon](https://img.icons8.com/cotton/50/000000/brick-wall.png)

## Key Refactoring Work
1. Successfully convert the entire Swing project to JavaFX.
2. Created a ```PauseMenu``` class by extracting it out from ```GameBoard``` class.
3. Created a ```Crack``` class from ```Brick``` class as only cement brick contains Crack properties. 
Doing this also achieve Liskov principle.
4. Created a ```Levels``` class from Wall class to achieve Single Responsibility principal.
5. Combined the makeSingleTypeLevel and makeChessboardLevel method in Levels class by extract method.
6. Uses Enum to replace constant integer numeric data in the entire project .
7. Extract an interface from brick, player, brick to create ```Entity``` and ```Movable``` interface class.
8. Created a ```PauseMenu``` class by extracting the block of code from ```GameBoard``` class.

## Additions and Extension
### Simple Features:
1. Added background image for MainMenu, PauseMenu and Scoreboard screen.
2. Added icon to the BrickBreaker and DebugConsole.
3. Created an info button along with an info screen.

### High Score List:
1. Created a pop-up summary level information box in the ```GameBoard Controller``` class.
2. Added permanent high score functionalities using file handling.
3. Created a Leaderboard in the ```ScoreBoard``` MVC class. 

### New and Exciting Features:
1. Added a pop-up player name input box creating a new ```NameInputBoxController```class. The nickname is intended to encourage the player's 
motivation to obtain a high score by displaying it on the leader board screen.
2. Implemented a score system in the ```GameLogic``` class.

|                      **Score**                       |     |                       
|:----------------------------------------------------:|-----|
|                  Completed a Level                   |     |
| Different Bricks gives different score when impacted |     |
|                     Loss a Ball                      |     |
|             Increase player score with               |     |


3. Added 2 new brick type (**Haste Brick**, **Black Stone Brick**) along with 2 new playable levels to the game.

## Design Pattern
1. Created a Ball and Brick Factory using Factory design pattern to replace the switch statement in ```Levels``` class.
3. Adhere the MVC pattern for ```GameBoard``` class and all the scene in the game.

### Additional Game Screen
1**Game info screen**

**Insert Images**
2. **Game Scoreboard**:
3. **Player name input box**

!!!! ADD github repo link



# Major Refactoring Activities
- Created a SceneManager class to handle different scene in the game.


# Small Refactoring Activities
- Renamed variable, parameter and remove unused code.
- Splitted DebugConsole, GameBoard, HomeMenu and PauseMenu into MVC.
- package
