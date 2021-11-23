package test;

import java.awt.*;

public class Levels {
    // TODO Note to Refactor: Don't use integer to represent the type of brick
    private static final int LEVELS_COUNT = 4;

    private Brick[][] levels;
    private int level;
    private Wall wall;
    private BrickFactory brickFactory;


    // Rectangle, 30, 3, 3, 300, 430
    public Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Wall wall){

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;
        this.wall = wall;
    }

    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCount, int lineCount, double brickSizeRatio, String brickType){
        brickFactory = new BrickFactory();
        brickCount -= brickCount % lineCount;
        int brickOnLine = brickCount / lineCount;
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;
        brickCount += lineCount / 2;
        Brick[] tmp  = new Brick[brickCount];
        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();
        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCount)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = brickFactory.getBrick(p,brickSize,brickType);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = brickFactory.getBrick(p,brickSize,brickType);
        }

        return tmp;
    }

    //TODO Diference: 1 Parameter, 6 lines of code
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCount, int lineCount, double brickSizeRatio, String brickTypeA, String brickTypeB){
        brickFactory = new BrickFactory();
        brickCount -= brickCount % lineCount;
        int brickOnLine = brickCount / lineCount;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;
        brickCount += lineCount / 2;
        Brick[] tmp  = new Brick[brickCount];
        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();
        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCount)
                break;

            int posX = i % brickOnLine;

            double x = posX * brickLen;

            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));

            tmp[i] = b ?  brickFactory.getBrick(p,brickSize,brickTypeA) : brickFactory.getBrick(p,brickSize,brickTypeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = brickFactory.getBrick(p,brickSize,brickTypeA);
        }

        return tmp;
    }

    // Refactor
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick");
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick","Cement Brick");
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Clay Brick","Steel Brick");
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"Steel Brick","Cement Brick");
        return tmp;
    }

    public void nextLevel(){
        // Postfix increment
//        bricks = levels[level++];
//        this.brickCount = bricks.length;
        wall.setBricks(levels[level++]);
        wall.setBrickCount(wall.getBricks().length);
    }

    public boolean hasLevel(){
        return level < levels.length;
    }

}
