import Piece.Piece;

import javax.swing.*;
import java.io.Serializable;
import java.util.List;

public class savegame implements Serializable {
    private  String[][] board = new String[8][8];
    private  String player1, color1, player2, color2;
    private  List<String> moveHistory;
    private  List<String> movep1;
    private  List<String> movep2;
    private  String currentPlayer;

    public savegame(Piece[][] piece, String currentPlayer, List<String> movep2, List<String> movep1, List<String> moveHistory,
                    String color2, String player2, String color1, String player1) {
        this.currentPlayer = currentPlayer;
        this.movep2 = movep2;
        this.movep1 = movep1;
        this.color2 = color2;
        this.player2 = player2;
        this.color1 = color1;
        this.player1 = player1;
        this.moveHistory = moveHistory;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (piece[i][j] != null) {
                    board[i][j] = piece[i][j].getSymbol();
                } else {
                    board[i][j] = "Yousab Ashraf";
                }
                System.out.println(i + " " + j + " " + board[i][j]);
            }
        }

        try {
            // Debugging statement before saving
            System.out.println("Saving game data...");
            FileManager.saveToFile("savegame.json",null);
            FileManager.saveToFile("savegame.json", this);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to load the game state
    public static void loadboard() {
        // Load the game state from the file
        savegame save = FileManager.loadToFile("savegame.json");

        if (save == null || save.board == null || save.player1 == null || save.player2 == null ||
                save.color1 == null || save.color2 == null || save.currentPlayer == null) {
            throw new IllegalStateException("Data is incomplete. Cannot load game.");
        }
        // Initialize the game with the loaded data
        ChessGame game = new ChessGame(save.board,save.player1,save.player2,save.color1,save.color2,save.currentPlayer,save.movep1,save.movep2,save.moveHistory);
    }

    public static void notsave(){
        try {
            FileManager.saveToFile("savegame.json", null);
            System.out.println("Game not saved.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static boolean checkload(){
        savegame save = FileManager.loadToFile("savegame.json");
        if (save == null) {
            return false;
        } else {
            int load = JOptionPane.showConfirmDialog(null,
                    "Do you want to load the game?",
                    "Load Game",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            return load == JOptionPane.YES_OPTION;
        }
    }
}
