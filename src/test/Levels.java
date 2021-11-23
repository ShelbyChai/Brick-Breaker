package test;

import java.awt.*;

public class Levels {
    // TODO Note to Refactor: Don't use integer to represent the type of brick
    private static final int LEVELS_COUNT = 4;
    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private Brick[][] levels;
    private int level;
    private Wall wall;


    // Rectangle, 30, 3, 3, 300, 430
    public Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Wall wall){

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;
        this.wall = wall;
    }

    // Rectangle, 30, 3, 3, Type
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        // 30
        brickCnt -= brickCnt % lineCnt;

        // 10
        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        // 31
        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        // tmp.length = 31, brickOnLine = 10, lineCnt = 3
        for(i = 0; i < tmp.length; i++){
            // The current line of brick
            int line = i / brickOnLine;
            // Terminate when the line == lineCount (break when i=30)
            if(line == lineCnt)
                break;
            // x = the current x position
            double x = (i % brickOnLine) * brickLen;
            // line 0,2 will proc, Second condition when its the first line
            // Half the width so the second line's 1st brick will be half width
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            // makeBrick check what type of Brick to make
            tmp[i] = makeBrick(p,brickSize,type);

//            System.out.println(i);
        }

        // This for loop add a brick to the end of every even row
        // y= width
        // BrickOnLine = 10, BrickHeight = 20
        // Since i is terminated on 30, thus this for loop only run once since tmp.length = 31
        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            // x always the same = 570
            double x = (brickOnLine * brickLen) - (brickLen / 2);

            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
        }

        return tmp;
    }

    // 0 ,0, 3, 3, CLAY, STEEL
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        //brickOnLine = 10
        int brickOnLine = brickCnt / lineCnt;

        // centerLeft = 4, centerRight = 6
        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        // brickLen = 60, brickHgt = 20
        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        // brickCnt = 31
        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;

            // poxX: value ranging from 0-9
            int posX = i % brickOnLine;

            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            // first or third row & even bricks || posX = 5,6 & second row
            // if b = true then typeA else steel typeB
            // first condition cause the first and third row's even number brick to be typeA (assume we start from 0)
            // second condition cause the second row's 15 and 16 brick to be type A
            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        return tmp;
    }

    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return out;
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
