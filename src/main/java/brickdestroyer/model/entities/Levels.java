package brickdestroyer.model.entities;

import brickdestroyer.model.entities_factory.BrickFactory;
import brickdestroyer.model.game.GameBoardModel;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * Levels class contains the properties and method for constructing
 * the in-game levels of the game of Brick Destroyer. There are a total of
 * 6 levels in the game and each contain at most 2 different types of brick.
 */
public class Levels {

    public static final int LEVELS_COUNT = 6;

    private final Brick[][] levels;

    /**
     * Creates a new 2d array brick with the given number of brick count,
     * line count and dimensionRatio of the brick for the game window.
     * @param brickCount The number of bricks for each level.
     * @param lineCount The number of lines of bricks for each level.
     * @param brickDimensionRatio The width/height dimension ratio of each brick in the level.
     */
    public Levels(int brickCount, int lineCount, double brickDimensionRatio){
        levels = makeLevels(brickCount, lineCount, brickDimensionRatio);
    }

    /**
     * Generate and return the total levels in a 2d array brick with the given properties of the brick.
     * This method played as a level factory role to return all levels in the game of Brick Destroyer.
     * @param brickCount an Int that represent the number of bricks for each level to be created.
     * @param lineCount an Int that represent the number of lines of bricks for each level to be created.
     * @param brickDimensionRatio The width/height dimension ratio of each brick in the level to be created.
     * @return a 2d array Brick Object that contains each level of the game.
     */
    public Brick[][] makeLevels(int brickCount, int lineCount, double brickDimensionRatio){
        Brick[][] level = new Brick[LEVELS_COUNT][];

        level[0] = makeLevel(brickCount, lineCount, brickDimensionRatio, "Clay Brick", null);
        level[1] = makeLevel(brickCount, lineCount, brickDimensionRatio,"Clay Brick","Cement Brick");
        level[2] = makeLevel(brickCount, lineCount, brickDimensionRatio,"Clay Brick","Steel Brick");
        level[3] = makeLevel(brickCount, lineCount, brickDimensionRatio,"Steel Brick","Cement Brick");
        level[4] = makeLevel(brickCount + 10, lineCount + 1, brickDimensionRatio,"Haste Brick", "Cement Brick");
        level[5] = makeLevel(brickCount + 20, lineCount + 2, brickDimensionRatio,"Haste Brick", "Black Stone Brick");
        return level;
    }

    /**
     * Generate the overall view structure, position of each brick in each level using the selected type of
     * brick and properties.
     * @param brickCnt an Int that represent the number of bricks of the current level.
     * @param lineCnt an Int that represent the number of lines of the current level.
     * @param brickDimRatio a Double that represent the width/height dimension ration of each brick in the current level.
     * @param brickTypeA a String that represent the first type of brick in the level.
     * @param brickTypeB a String that represent the second type of brick in the level.
     * @return an array of Brick object that represent a single level in the game.
     */
    private Brick[] makeLevel(int brickCnt, int lineCnt, double brickDimRatio, String brickTypeA, String brickTypeB){
        int brickCount = brickCnt;
        brickCount -= brickCount % lineCnt;
        int brickOnLine = brickCount / lineCnt;
        double brickLength = (double)GameBoardModel.DEF_WIDTH / brickOnLine;
        double brickHeight = brickLength / brickDimRatio;

        brickCount += lineCnt / 2;
        Brick[] level  = new Brick[brickCount];
//        Brick[] level  = new Brick[1];

        Dimension2D brickSize = new Dimension2D((int) brickLength,(int) brickHeight);
        Point2D brickPosition;

        int i;
        for(i = 0; i < level.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double positionX = (i % brickOnLine) * brickLength;
            positionX =(line % 2 == 0) ? positionX : (positionX - (brickLength / 2));
            double positionY = (line) * brickHeight;
            brickPosition = new Point2D(positionX,positionY);

            level[i] = generateBrickType(chooseBrick(line, i, brickOnLine), brickPosition, brickSize, brickTypeA, brickTypeB);
        }

        for(double y = brickHeight; i < level.length; i++, y += 2 * brickHeight){
            double x = (brickOnLine * brickLength) - (brickLength / 2);
            brickPosition = new Point2D(x,y);
            level[i] = makeBrick(brickTypeA, brickPosition,brickSize);
        }

        return level;
    }

    /**
     * This method is called by makeLevel() method to decide
     * @param line an Int that represent the current line of the brick to be generated.
     * @param brickIndex an Int that represent the current index number of the brick to be generated.
     * @param brickOnLine an Int that represent the number of bricks of the current line of brick.
     * @return true if the given brick should be created on a specific position using the algorithm in makeLevel method().
     */
    private Boolean chooseBrick(int line, int brickIndex, int brickOnLine) {
        int posX = brickIndex % brickOnLine;
        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        return ((line % 2 == 0 && brickIndex % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
    }

    /**
     * This method is called by generateBrickType() and makeLevel() method to
     * return the selected type of brick object created by the brick factory.
     * @param brickType a String of the brick type to be created.
     * @param position a Point2D x and y value that represent upper-left bound position of the brick to be created.
     * @param brickSize a Dimension2D value that represent the width/height dimension ratio of the brick.
     * @return a Brick that is generated by the brick factory using the given properties.
     */
    private Brick makeBrick(String brickType, Point2D position, Dimension2D brickSize) {
        BrickFactory brickFactory = new BrickFactory();
        return brickFactory.getBrickType(brickType, position, brickSize);
    }

    /**
     * This method is called by generateBrickType method() to decide whether the level
     * contain one type of two type of bricks in the level.
     * @param brickTypeA a String that represent first type of brick on the level.
     * @param brickTypeB a String that represent the second type of brick on the level.
     * @return true if there is only one type of brick on the level, false if there is two.
     */
    private Boolean isOneType(String brickTypeA, String brickTypeB) {
        return brickTypeA == null || brickTypeB == null;
    }

    /**
     * This method generate the selected brick type using the boolean value of isOneType()
     * method to the makeLevel() method.
     * @param b a Boolean value that is true if the level only contains a single type of brick.
     * @param brickPosition a Point2D x and y value that represent upper-left bound position of the brick to be created.
     * @param brickSize a Dimension2D value that represent the width/height dimension ratio of the brick to be created.
     * @param brickTypeA a String that represent the first type of brick.
     * @param brickTypeB a String that represent the second type of brick.
     * @return a selected Brick object with the given properties.
     */
    private Brick generateBrickType(boolean b, Point2D brickPosition, Dimension2D brickSize, String brickTypeA, String brickTypeB) {
        if (isOneType(brickTypeA, brickTypeB))
            return makeBrick(brickTypeA, brickPosition,brickSize);
        else
            return b ? makeBrick(brickTypeA, brickPosition,brickSize) : makeBrick(brickTypeB, brickPosition,brickSize);
    }

    /**
     * Getter method for the total levels in the game.
     * This method is called to change the level of the game.
     * @return the 2d array of brick which represent the levels.
     */
    public Brick[][] getLevels(){return levels;}
}
