package brickdestroyer.model;

import brickdestroyer.model.abstract_entities.Brick;
import brickdestroyer.model.entities_factory.BrickFactory;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Levels {

    public static final int LEVELS_COUNT = 4;

    private final Brick[][] levels;
    private final Rectangle drawArea;
    private final int brickCount;
    private final int lineCount;
    private final double brickDimensionRatio;

    public Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        this.drawArea = drawArea;
        this.brickCount = brickCount;
        this.lineCount = lineCount;
        this.brickDimensionRatio = brickDimensionRatio;

        levels = makeLevels();
    }

    // TODO Level factory maybe
    private Brick[][] makeLevels(){
        Brick[][] level = new Brick[LEVELS_COUNT][];

        level[0] = makeLevel("Clay Brick", null);
        level[1] = makeLevel("Clay Brick","Cement Brick");
        level[2] = makeLevel("Clay Brick","Steel Brick");
        level[3] = makeLevel("Steel Brick","Cement Brick");
        return level;
    }

    private Brick[] makeLevel(String brickTypeA, String brickTypeB){
        int brickCount = this.brickCount;
        brickCount -= brickCount % this.lineCount;
        int brickOnLine = brickCount / this.lineCount;
        double brickLength = drawArea.getWidth() / brickOnLine;
        double brickHeight = brickLength / this.brickDimensionRatio;

        brickCount += this.lineCount / 2;
        Brick[] brickWall  = new Brick[brickCount];
        Dimension2D brickSize = new Dimension2D((int) brickLength,(int) brickHeight);
        Point2D brickPosition;

        int brickIndex;
        for(brickIndex = 0; brickIndex < brickWall.length; brickIndex++){
            int line = brickIndex / brickOnLine;
            if(line == this.lineCount)
                break;
            double positionX = (brickIndex % brickOnLine) * brickLength;
            positionX =(line % 2 == 0) ? positionX : (positionX - (brickLength / 2));
            double positionY = (line) * brickHeight;
            brickPosition = new Point2D(positionX,positionY);

            brickWall[brickIndex] = generateBrickType(chooseBrick(line, brickIndex, brickOnLine), brickPosition, brickSize, brickTypeA, brickTypeB);
        }

        for(double y = brickHeight; brickIndex < brickWall.length; brickIndex++, y += 2 * brickHeight){
            double x = (brickOnLine * brickLength) - (brickLength / 2);
            brickPosition = new Point2D(x,y);
            brickWall[brickIndex] = makeBrick(brickTypeA, brickPosition,brickSize);
        }

        return brickWall;
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
