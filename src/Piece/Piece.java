package Piece;

import java.io.Serializable;

public abstract class Piece {
    protected String color;
    protected int x, y;

    public Piece(String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public String getColor() {
        return color;
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract String getSymbol();

    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board);
}

