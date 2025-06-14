package Piece;

import java.io.Serializable;

public class Knight extends Piece  {
    public Knight(String color, int x, int y) {
        super(color, x, y);
    }



    public String getSymbol() {
        return color.equals("white") ? "Knight_white" : "Knight_black";
    }


    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            Piece target = board[endX][endY];
            return target == null || !target.getColor().equals(color);
        }
        return false;
    }
}

