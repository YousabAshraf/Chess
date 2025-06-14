package Piece;


import java.io.Serializable;

public class Rook extends Piece {
    public  boolean firstMove ;
    public Rook(String color, int x, int y) {
        super(color, x, y);
        firstMove=true;
    }


    public String getSymbol() {
        return color.equals("white") ? "Rook_white" : "Rook_black";
    }

    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {

        if (startX != endX && startY != endY) {
            return false;
        }

        if (startX == endX) {
            int minY = Math.min(startY, endY);
            int maxY = Math.max(startY, endY);
            for (int y = minY + 1; y < maxY; y++) {
                if (board[startX][y] != null) {
                    return false;
                }
            }
            //            this.firstMove = firstMove;
            Piece target = board[endX][endY];
            return target == null || !target.getColor().equals(color);

        }
        else{
            int minX = Math.min(startX, endX);
            int maxX = Math.max(startX, endX);
            for (int x = minX + 1; x < maxX; x++) {
                if (board[x][startY] != null){
                    return false;
                }
            }
//            this.firstMove = firstMove;
            Piece target = board[endX][endY];
            return target == null || !target.getColor().equals(color);
        }

    }

//    public boolean isFirstMove() {
//        return firstMove;
//    }
//
//    public void setFirstMove(boolean firstMove) {
//        this.firstMove = firstMove;
//    }
    public boolean canCastle(Piece[][] board) {
        return this.firstMove;
    }
}