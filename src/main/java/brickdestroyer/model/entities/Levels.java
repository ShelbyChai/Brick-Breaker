package brickdestroyer.model.entities;

import brickdestroyer.model.entities_factory.BrickFactory;
import brickdestroyer.model.game.GameBoardModel;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

public class Levels {

    public static final int LEVELS_COUNT = 6;

    private final Brick[][] levels;

    public Levels(int brickCount, int lineCount, double brickDimensionRatio){
        levels = makeLevels(brickCount, lineCount, brickDimensionRatio);
    }

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

    private Brick[] makeLevel(int brickCnt, int lineCnt, double brickDimRatio, String brickTypeA, String brickTypeB){
        int brickCount = brickCnt;
        brickCount -= brickCount % lineCnt;
        int brickOnLine = brickCount / lineCnt;
        double brickLength = (double)GameBoardModel.DEF_WIDTH / brickOnLine;
        double brickHeight = brickLength / brickDimRatio;

        brickCount += lineCnt / 2;
        Brick[] level  = new Brick[brickCount];

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

    private Boolean chooseBrick(int line, int brickIndex, int brickOnLine) {
        int posX = brickIndex % brickOnLine;
        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        return ((line % 2 == 0 && brickIndex % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
    }

    private Brick makeBrick(String brickType, Point2D position, Dimension2D brickSize) {
        BrickFactory brickFactory = new BrickFactory();
        return brickFactory.getBrickType(brickType, position, brickSize);
    }

    private Boolean isOneType(String brickTypeA, String brickTypeB) {
        return brickTypeA == null || brickTypeB == null;
    }

    private Brick generateBrickType(boolean b, Point2D brickPosition, Dimension2D brickSize, String brickTypeA, String brickTypeB) {
        if (isOneType(brickTypeA, brickTypeB))
            return makeBrick(brickTypeA, brickPosition,brickSize);
        else
            return b ? makeBrick(brickTypeA, brickPosition,brickSize) : makeBrick(brickTypeB, brickPosition,brickSize);
    }

    public Brick[][] getLevels(){return levels;}
}
