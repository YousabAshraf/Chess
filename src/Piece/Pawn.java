package Piece;

import javax.swing.*;
import java.io.Serializable;
public class Pawn extends Piece  {
    private boolean firstMove;
    private boolean enPassantEligible;
    public Pawn(String color, int x, int y) {
        super(color, x, y);
        this.firstMove = true;
        this.enPassantEligible = false;
    }



    public String getSymbol() {
        return color.equals("white") ? "Pawn_white" : "Pawn_black";
    }


    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        int direction = color.equals("white") ? -1 : 1;

        if ((color.equals("black") && endX <= startX) || (color.equals("white") && endX >= startX)) {
            return false;
        }
        else if (endX == startX + direction && endY == startY && board[endX][endY] == null) {
            return true;
        }
        else if (firstMove && endX == startX + 2 * direction && endY == startY &&
                board[startX + direction][endY] == null && board[endX][endY] == null) {
            firstMove = false;
            enPassantEligible = true;
            return true;
        }
        else if (endX == startX + direction && Math.abs(endY - startY) == 1 && board[endX][endY] != null &&
                !board[endX][endY].getColor().equals(color)) {
            return true;
        }
        else if (endX == startX + direction && Math.abs(endY - startY) == 1 && (endX==2||endX==5)) {
            Piece adjacentPiece = board[startX][endY];
            if (adjacentPiece instanceof Pawn && ((Pawn) adjacentPiece).isEnPassantEligible() &&
                    !adjacentPiece.getColor().equals(color)) {
//                System.out.println("EnPassantEligible done");
//                board[startX][endY] = null;
                return true;
            }
            return false;
        }
        else {
            return false;
        }
    }

    public boolean isEnPassantEligible() {
        return enPassantEligible;
    }

    public static void promotePawn(int x, int y, Piece[][] board, String currentPlayer) {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        String choice = null;

        while (choice == null) {
            choice = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose a piece for promotion:",
                    "Promotion",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == null) {
                JOptionPane.showMessageDialog(null, "You must choose a piece for promotion!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        Piece newPiece = null;
        String color = currentPlayer;

        switch (choice) {
            case "Queen":
                newPiece = new Queen(color, x, y);
                break;
            case "Rook":
                newPiece = new Rook(color, x, y);
                break;
            case "Bishop":
                newPiece = new Bishop(color, x, y);
                break;
            case "Knight":
                newPiece = new Knight(color, x, y);
                break;
        }

        if (newPiece != null) {
            board[x][y] = newPiece;
            System.out.println("Pawn promoted to " + choice + " at (" + x + ", " + y + ")");
        } else {
            System.out.println("Failed to create a new piece for promotion.");
        }
    }

    public static void pawn_promotion(int row,int col,Piece[][] grid,String currentPlayer,Piece activePiece){
     ((Pawn)activePiece).firstMove=false;
     if ((activePiece.getColor().equals("white") && row == 0) ||
             (activePiece.getColor().equals("black") && row == 7)) {
         Pawn.promotePawn(row, col, grid, currentPlayer);
     }

 }

    public static void en_passant(int activeRow,int activeCol,int row,int col,Piece[][] grid,String currentPlayer){
     int direction = currentPlayer.equals("white") ? -1 : 1;
     if ((row == activeRow + direction && Math.abs(activeCol - col) == 1 && (row==2||row==5))) {
         if (grid[activeRow][col] instanceof Pawn && ((Pawn) grid[activeRow][col]).isEnPassantEligible() &&
                 !grid[activeRow][col].getColor().equals(currentPlayer)) {
//                                System.out.println("EnPassantEligible done");
             grid[activeRow][col] = null;
         }
     }
 }

}

