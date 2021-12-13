package brickdestroyer.model.game;

import brickdestroyer.controller.NameInputBoxController;

import java.io.*;
import java.util.*;


/**
 * Score Board Model is responsible to provide all the data
 * needed to run the score board.
 */
public class ScoreBoardModel {
    public static final int DEF_SCORE_BOARD = 5;

    private final GameLogic gameLogic;
    private final NameInputBoxController nameInputBoxController;
    private HashMap<String, Integer> playerRecord;


    /**
     * @param gameLogic Game logic of the Brick Destroyer game.
     * @param nameInputBoxController Controller object of the Player name input box.
     */
    public ScoreBoardModel(GameLogic gameLogic, NameInputBoxController nameInputBoxController){
        playerRecord = new HashMap<String, Integer>();
        this.gameLogic = gameLogic;
        this.nameInputBoxController = nameInputBoxController;
        readHighScore();
    }

    /**
     * This method is called to read the score in the high_score.txt file in the
     * resources/text directory and store it on a Hash Map variable named playerRecord.
     */
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinkedHashMap<String, Integer> sortedPlayerRecord = sortScore(getPlayerRecord());
        setPlayerRecord(sortedPlayerRecord);
    }

    /**
     * This method is called by readHighScore() and updateHighScore() method to sort
     * and return the playerRecord.
     * @param playerRecord a HashMap that contains the information of player name and
     *                     player score of the current game.
     * @return a LinkedHashMap that contains the sorted playerRecord list.
     */
    private LinkedHashMap<String, Integer> sortScore(HashMap<String, Integer> playerRecord) {
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        playerRecord.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(i -> reverseSortedMap.put(i.getKey(), i.getValue()));

        return reverseSortedMap;
    }

    /**
     * This method is called to update the score list in the playerRecord
     * of the current player score and name.
     */
    public void updateScoreList() {
        HashMap<String, Integer> tempPlayerRecord = getPlayerRecord();
        String currentPlayerName = getPlayerName();
        int currentScore = getGameLogic().getScore();

        tempPlayerRecord.put(currentPlayerName, currentScore);

        LinkedHashMap<String, Integer> sortedPlayerRecord = sortScore(tempPlayerRecord);

        setPlayerRecord(sortedPlayerRecord);
        writeHighScore();
    }

    /**
     * This method is called to write the updated list of player score and name into the high_score.txt
     * file in the resources/text directory.
     */
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

    /**
     * Getter method to return the game logic object.
     * @return the game logic object.
     */
    public GameLogic getGameLogic() {
        return gameLogic;
    }

    /**
     * Getter method for the current name of the player.
     * @return a String that contains the name of the player.
     */
    public String getPlayerName() {
        return nameInputBoxController.getPlayerName();
    }

    /**
     * Getter method for the overall HashMap of player record that contains the name and
     * score achieved by the player.
     * @return a HashMap that contains the overall player record.
     */
    public HashMap<String, Integer> getPlayerRecord() {
        return playerRecord;
    }

    /**
     * Setter method for the HashMap of player record. This method is called to retrieve the
     * sorted list of player record
     * @param playerRecord a HashMap that represent the value to be assigned.
     */
    public void setPlayerRecord(HashMap<String, Integer> playerRecord) {
        this.playerRecord = playerRecord;
    }

    /**
     * Getter method for the Controller object of the name input box.
     * @return the Controller object of the name input box.
     */
    public NameInputBoxController getPlayerBoxController() {
        return nameInputBoxController;
    }
}
