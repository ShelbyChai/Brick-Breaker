package test;

import java.awt.*;

public class Levels {

    private static final int LEVELS_COUNT = 4;

    private Brick[][] levels;
    private int level;
    private GameLogic gameLogic;
    private BrickFactory brickFactory;


    public Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, GameLogic gameLogic){

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;
        this.gameLogic = gameLogic;
    }

    // Refactor: makeSingleTypeLevel create a local variable instead of modifying the parameter
    private Brick[] makeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, String brickTypeA, String brickTypeB){
        brickFactory = new BrickFactory();
        int brickCount = brickCnt;
        brickCount -= brickCount % lineCnt;
        int brickOnLine = brickCount / lineCnt;
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCount += lineCnt / 2;
        Brick[] tmp  = new Brick[brickCount];
        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();
        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            tmp[i] = generateBrickType(chooseBrick(line, i, brickOnLine), p, brickSize, brickTypeA, brickTypeB);

        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = brickFactory.getBrick(p,brickSize,brickTypeA);
        }

        return tmp;
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

    public Brick generateBrickType(boolean b, Point p, Dimension brickSize, String brickTypeA, String brickTypeB) {
        if (isOneType(brickTypeA, brickTypeB))
            return brickFactory.getBrick(p,brickSize,brickTypeA);
        else
            return b ?  brickFactory.getBrick(p,brickSize,brickTypeA) : brickFactory.getBrick(p,brickSize,brickTypeB);
    }

    // TODO Level factory?
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick", null);
        tmp[1] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick","Cement Brick");
        tmp[2] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick","Steel Brick");
        tmp[3] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Steel Brick","Cement Brick");
        return tmp;
    }

    public void nextLevel(){
        gameLogic.setBricks(levels[level++]);
        gameLogic.setBrickCount(gameLogic.getBricks().length);
    }

    public Brick[] incrementLevels() {
        return levels[level++];
    }
    public boolean hasLevel(){
        return level < levels.length;
    }

}
