package Piece;


public class Bishop extends Piece  {
    public Bishop(String color, int x, int y) {
        super(color, x, y);
    }


    public String getSymbol() {
        return color.equals("white") ? "Bishop_white" : "Bishop_black";
    }

    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        if (dx != dy) {
            return false;
        }

        int xDirection = (endX > startX) ? 1 : -1;
        int yDirection = (endY > startY) ? 1 : -1;

        for (int i = 1; i < dx; i++) {
            if (board[startX + i * xDirection][startY + i * yDirection] != null) {
                return false;
            }
        }

        Piece target = board[endX][endY];
        return target == null || !target.getColor().equals(color);
    }

}