package brickdestroyer.Actor;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Levels {

    private static final int LEVELS_COUNT = 4;

    final private Brick[][] levels;

    public Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
    }

    // Refactor: makeSingleTypeLevel create a local variable instead of modifying the parameter
    private Brick[] makeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, String brickTypeA, String brickTypeB){
        int brickCount = brickCnt;
        brickCount -= brickCount % lineCnt;
        int brickOnLine = brickCount / lineCnt;
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCount += lineCnt / 2;
        Brick[] brickWall  = new Brick[brickCount];
        Dimension2D brickSize = new Dimension2D((int) brickLen,(int) brickHgt);
        Point2D p;

        int i;
        for(i = 0; i < brickWall.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p = new Point2D(x,y);

            brickWall[i] = generateBrickType(chooseBrick(line, i, brickOnLine), p, brickSize, brickTypeA, brickTypeB);
        }

        for(double y = brickHgt; i < brickWall.length; i++, y += 2 * brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p = new Point2D(x,y);
            brickWall[i] = makeBrick(brickTypeA, p,brickSize);
        }

        return brickWall;
    }

    private Boolean chooseBrick(int line, int i, int brickOnLine) {
        int posX = i % brickOnLine;
        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        return ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
    }

    private Brick makeBrick(String brickType, Point2D position, Dimension2D brickSize) {
        BrickFactory brickFactory = new BrickFactory();
        return brickFactory.getBrickType(brickType, position, brickSize);
    }

    private Boolean isOneType(String brickTypeA, String brickTypeB) {
        return brickTypeA == null || brickTypeB == null;
    }

    private Brick generateBrickType(boolean b, Point2D p, Dimension2D brickSize, String brickTypeA, String brickTypeB) {
        if (isOneType(brickTypeA, brickTypeB))
            return makeBrick(brickTypeA, p,brickSize);
        else
            return b ? makeBrick(brickTypeA, p,brickSize) : makeBrick(brickTypeB, p,brickSize);
    }

    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] level = new Brick[LEVELS_COUNT][];

        level[0] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick", null);
        level[1] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick","Cement Brick");
        level[2] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick","Steel Brick");
        level[3] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Steel Brick","Cement Brick");
        return level;
    }

    public Brick[][] getLevels(){return levels;}

    public int getLevelsCount(){return LEVELS_COUNT;}
}

