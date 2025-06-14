package Piece;

import java.io.Serializable;

public class Queen extends Piece  {
    public Queen(String color, int x, int y) {
        super(color, x, y);
    }



    public String getSymbol() {
        return color.equals("white") ? "Queen_white" : "Queen_black";
    }

    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        Bishop bishop = new Bishop(color, startX, startY);
        Rook rook = new Rook(color, startX, startY);
        return bishop.isValidMove(startX, startY, endX, endY, board) ||
                rook.isValidMove(startX, startY, endX, endY, board);
    }

}