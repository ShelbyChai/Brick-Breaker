package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;

public class PauseMenu implements MouseListener, MouseMotionListener {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private boolean showPauseMenu;
    private Font menuFont;
    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;
    private GameLogic gameLogic;
    private JComponent owner;
    private GameBoard gameBoard;

    public PauseMenu(GameLogic gameLogic, JComponent jComponent, GameBoard gameBoard) {
        super();
        this.owner = jComponent;
        this.gameBoard = gameBoard;
        this.gameLogic = gameLogic;

        this.initialize();
        showPauseMenu = false;
        strLen = 0;
        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);
    }

    private void initialize(){
        this.owner.addMouseListener(this);
        this.owner.addMouseMotionListener(this);
    }

    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.owner.getWidth() - strLen) / 2;
        int y = this.owner.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.owner.getWidth() / 8;
        y = this.owner.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);
        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            owner.repaint();
        }
        else if(restartButtonRect.contains(p)){
            gameBoard.setMessage("Restarting Game...");
            gameLogic.ballReset();
            gameLogic.wallReset();
            showPauseMenu = false;
            owner.repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }
    }



    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {}

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.owner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.owner.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.owner.setCursor(Cursor.getDefaultCursor());
        }
    }

    public boolean isShowPauseMenu() {
        return !showPauseMenu;
    }

    public void setShowPauseMenu(boolean showPauseMenu) {
        this.showPauseMenu = showPauseMenu;
    }

    public void displayMenu(Graphics2D g2d) {
        if(showPauseMenu)
            drawMenu(g2d);
    }
}
