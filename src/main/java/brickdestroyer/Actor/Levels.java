package brickdestroyer.Actor;

import brickdestroyer.GameBoard.GameBoardModel;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Levels {

    private static final int LEVELS_COUNT = 4;

    private Brick[][] levels;
    private int level;
    private GameBoardModel gameBoardModel;


    public Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, GameBoardModel gameBoardModel){

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;
        this.gameBoardModel = gameBoardModel;
    }

    // Refactor: makeSingleTypeLevel create a local variable instead of modifying the parameter
    private Brick[] makeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, String brickTypeA, String brickTypeB){
        int brickCount = brickCnt;
        brickCount -= brickCount % lineCnt;
        int brickOnLine = brickCount / lineCnt;
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCount += lineCnt / 2;
        Brick[] tmp  = new Brick[brickCount];
        Dimension2D brickSize = new Dimension2D((int) brickLen,(int) brickHgt);
        Point2D p = new Point2D(0,0);
        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p = new Point2D(x,y);
//            p.setLocation(x,y);

            tmp[i] = generateBrickType(chooseBrick(line, i, brickOnLine), p, brickSize, brickTypeA, brickTypeB);

        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p = new Point2D(x,y);
//            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,brickTypeA);
        }

        return tmp;
    }

    public Brick makeBrick(Point2D point, Dimension2D brickSize, String brickType) {
        BrickFactory brickFactory = new BrickFactory();
        return brickFactory.getBrick(point, brickSize, brickType);
    }

    public Boolean isOneType(String brickTypeA, String brickTypeB) {
        return brickTypeA == null || brickTypeB == null;
    }

    public Boolean chooseBrick(int line, int i, int brickOnLine) {
        int posX = i % brickOnLine;
        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        return ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
    }

    public Brick generateBrickType(boolean b, Point2D p, Dimension2D brickSize, String brickTypeA, String brickTypeB) {
        if (isOneType(brickTypeA, brickTypeB))
            return makeBrick(p,brickSize,brickTypeA);
        else
            return b ?  makeBrick(p,brickSize,brickTypeA) : makeBrick(p,brickSize,brickTypeB);
    }

    // TODO Level factory?
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];

        // TODO test crack path draw
        tmp[0] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Cement Brick", "Cement Brick");
//        tmp[0] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Steel Brick","Steel Brick");
//        tmp[0] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick", null);
        tmp[1] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick","Cement Brick");
        tmp[2] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick","Steel Brick");
        tmp[3] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Steel Brick","Cement Brick");
        return tmp;
    }

    public void nextLevel(){
        gameBoardModel.setBricks(levels[level++]);
        gameBoardModel.setBrickCount(gameBoardModel.getBricks().length);
    }

    public boolean hasLevel(){
        return level < levels.length;
    }

}

