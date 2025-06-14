package Piece;

import java.io.Serializable;

public class King extends Piece  {
    public boolean firstMove;
    public King(String color, int x, int y) {
        super(color, x, y);
        firstMove=true;
    }

    public String getSymbol() {
        return color.equals("white") ? "King_white" : "King_black";
    }

    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        if (dx <= 1 && dy <= 1
//                &&!checkMove(startX, startY, endX, endY, board)
        ) {
            Piece target = board[endX][endY];
            return target == null || !target.getColor().equals(color);
        }

        if (firstMove && endY == 6 && dx == 0
//                &&!checkMove(startX, startY, endX, endY, board)
        ) {
            Piece rookPiece = board[startX][7];
            if (rookPiece instanceof Rook && ((Rook) rookPiece).canCastle(board)) {
                for (int i = 1; i < 3; i++) {
                    if (board[startX][startY + i] != null) {
                        return false;
                    }
                }
                return true;
            }
        }

        if (firstMove && endY == 2 && dx == 0
//                &&!checkMove(startX, startY, endX, endY, board)
                ) {
            Piece rookPiece = board[startX][0];
            if (rookPiece instanceof Rook && ((Rook) rookPiece).canCastle(board)) {
                for (int i = 1; i < 4; i++) {
                    if (board[startX][startY - i] != null) {
                        return false;
                    }
                }
                return true;
            }
        }

        return false;
    }
    public static void castling(int activeRow, int activeCol, int row, int col, Piece[][] grid, Piece activePiece){
        int dx = Math.abs(row - activeRow);
        if (((King) activePiece).firstMove && col == 2 && dx == 0) {
            Piece rookPiece = grid[activeRow][0];
            boolean done=true;
            if (rookPiece instanceof Rook && ((Rook) rookPiece).canCastle(grid)) {
                for (int i = 1; i < 4; i++) {
                    if (grid[activeRow][activeCol - i] != null&&!(grid[activeRow][activeCol - i] instanceof King)) {
                        done = false;
                    }
                }
            }
            if (done) {
                grid[activeRow][3] = grid[activeRow][0];
                grid[activeRow][0] = null;
                ((King) activePiece).firstMove = false;
                ((Rook) grid[activeRow][3]).firstMove=false;
            }
        }
        else if (((King) activePiece).firstMove && col == 6 && dx == 0) {
            boolean done=true;
            Piece rookPiece = grid[activeRow][7];
            if (rookPiece instanceof Rook && ((Rook) rookPiece).canCastle(grid)) {
                for (int i = 1; i < 3; i++) {
                    if (grid[activeRow][activeCol + i] != null&&!(grid[activeRow][activeCol + i] instanceof King)) {
                        done = false;
                    }
                }
                if (done) {
                    grid[activeRow][5] = grid[activeRow][7];
                    grid[activeRow][7] = null;
                    ((King) activePiece).firstMove = false;
                    ((Rook) grid[activeRow][5]).firstMove=false;
                }

            }
        }
        else {
            ((King) activePiece).firstMove = false;
        }
    }

//    public boolean checkMove(int startX, int startY, int endX, int endY, Piece[][] board) {
//        Piece originalPiece = board[endX][endY];
//        board[endX][endY] = board[startX][startY];
//        board[startX][startY] = null;
//
//        boolean isInCheck = false;
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                Piece opponentPiece = board[row][col];
//                if (opponentPiece != null && !opponentPiece.getColor().equals(board[endX][endY].getColor())) {
//                    if (opponentPiece.isValidMove(row, col, endX, endY, board)) {
//                        System.out.println("check move");
//                        isInCheck = true;
//                        break;
//                    }
//                }
//            }
//            if (isInCheck) {
//                break;
//            }
//        }
//
//        board[startX][startY] = board[endX][endY];
//        board[endX][endY] = originalPiece;
//
//        return isInCheck;
//    }
}
