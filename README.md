# Brick Breaker ![brick-icon](https://img.icons8.com/cotton/50/000000/brick-wall.png)

## Key Refactoring Work
1. Convert the entire project to JavaFX while retaining the basic game logic .
2. Change package name to a more meaningful name and adhere the MVC package organization.
3. Change method access modifier and encapsulate variable field throughout the classes.
4. Removed method overloading in ```Crack``` class by renaming one of the makeCrack() method to crack as only method that 
perform the same task can use method overloading.
5. Introduce polymorphism in ```Brick``` class on setImpact() method for non-crackable brick class.
6. Created a ```PauseMenu``` class by extracting it out from ```GameBoard``` class.
7. Created a ```Crack``` class from ```Brick``` class as only cement brick contains Crack properties. 
Doing this also achieve Liskov principle.
8. Created a ```Levels``` class from Wall class to achieve Single Responsibility principal.
9. Combined the makeSingleTypeLevel and makeChessboardLevel method in Levels class by extract method.
10. Uses Enum to replace constant integer type such as the impactDirection in ```Brick``` Class and the Direction in ```Crack``` class.
11. Extract an interface from brick, player, brick to create ```Entity``` and ```Movable``` interface class.
12. Created a ```PauseMenu``` class by extracting the block of code from ```GameBoard``` class.

## Additions and Extension
### Simple Features:
1. Improve the overall design of the game scene.
2. Added background image for MainMenu, PauseMenu and Scoreboard screen.
3. Added icon to the BrickBreaker and DebugConsole.
4. Created an info button along with an info screen.

### High Score List:
1. Created a pop-up summary level information box in the ```GameBoard Controller``` class.
2. Added permanent high score functionalities using file handling.
3. Created a Leaderboard in the ```ScoreBoard``` MVC class. 

### Exciting Features:
1. Added a pop-up player name input box creating a new ```NameInputBoxController```class to encourage competition between player.
2. Implemented a score system in the ```GameLogic``` class.
   - Score Manipulation:
     - Increase: Complete a level, different bricks provide different score, additional score base on the ball count left.
     - Decrease: Drop a ball.
3. Created a congratulations pop-up info box.

## Additional playable levels
1. Added 2 extra levels.
    - First added level: Contains 40 brickCount, ```HasteBrick``` and ```CementBrick```.
    - - First added level: Contains 50 brickCount, ```HasteBrick``` and ```BlackStone Brick```.
2. Added 2 extra brick types.
    - ```HasteBrick```: Increase the speed of the ball after collision.
    - ```BlackStoneBrick```: Contains the combination property of ```SteelBrick``` and ```CementBrick```.

## Design Pattern
1. Adhere the MVC pattern for ```GameBoard``` class and all the scene in the game for easy regression tests in the future.
2. Created a handful amount of models for controller.
3. Created a ```SceneManager``` class which handle the stage and scene transition in the entire game.
4. Created a ```BallFactory``` and ```BrickFactory``` class to replace the switch statement in Levels class. This also to promote abstraction and reduce the coupling of code.

## More:
1. Created a button for Info Screen and Leaderboard.
    - Info Screen: Display the game controls.
    - LeaderBoard: Display the top 5 player score from a txt file.
2. Improved the view and design for all the FXML file using CSS, images, Icons and.
