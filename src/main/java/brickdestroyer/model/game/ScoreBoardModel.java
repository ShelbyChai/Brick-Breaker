package brickdestroyer.model.game;

import brickdestroyer.controller.PlayerBoxController;

import java.io.*;
import java.util.*;


public class ScoreBoardModel {
    public static final int DEF_SCORE_BOARD = 5;

    private final GameLogic gameLogic;
    private final PlayerBoxController playerBoxController;
    private HashMap<String, Integer> playerRecord;


    public ScoreBoardModel(GameLogic gameLogic, PlayerBoxController playerBoxController){
        playerRecord = new HashMap<String, Integer>();
        this.gameLogic = gameLogic;
        this.playerBoxController = playerBoxController;
        readHighScore();
    }

    private void readHighScore() {
        File file = new File("src/main/resources/brickdestroyer/text/high_score.txt");

        if (file.length() == 0)
            return;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            try {
                while (br.ready()) {
                    String[] nameScore = br.readLine().split(", ");
                    String playerName = nameScore[0];
                    int playerScore = Integer.parseInt(nameScore[1]);
                    playerRecord.put(playerName, playerScore);
                }

            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
            br.close();
        } catch (FileNotFoundException e ) {
            System.err.println("high_score.txt could not be read");
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void writeHighScore() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/brickdestroyer/text/high_score.txt"))){
            for (String player : playerRecord.keySet()) {
                bw.write(player + ", " + playerRecord.get(player));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public String getPlayerName() {
        return playerBoxController.getPlayerName();
    }

    public HashMap<String, Integer> getPlayerRecord() {
        return playerRecord;
    }

    public void setPlayerRecord(HashMap<String, Integer> playerRecord) {
        this.playerRecord = playerRecord;
    }

    public PlayerBoxController getPlayerBoxController() {
        return playerBoxController;
    }
}
