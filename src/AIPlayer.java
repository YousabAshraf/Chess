import Piece.Piece;

public class AIPlayer {
    private String color;

    public ChessGame game;

    public AIPlayer(String color,ChessGame game) {
        this.color = color;
        this.game=game;
    }

    public String getColor() {
        return color;
    }

    public boolean makeMove(Piece[][] board) {
        int bestStartX = -1, bestStartY = -1, bestEndX = -1, bestEndY = -1;
        int bestScore = Integer.MIN_VALUE;

        // البحث عن أفضل حركة ممكنة
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getColor().equals(color)) {
                    for (int x = 0; x < board.length; x++) {
                        for (int y = 0; y < board[x].length; y++) {
                            if (game.isValidMove(i, j, x, y))
                                if (piece.isValidMove(i, j, x, y, board)) {

                                    int score = evaluateMove(i, j, x, y, board);
                                    if (game.isInCheck(color)) {
                                        score += 50;
                                    }
                                    if (score > bestScore) {
                                        bestScore = score;
                                        bestStartX = i;
                                        bestStartY = j;
                                        bestEndX = x;
                                        bestEndY = y;
                                    }
                                }
                        }
                    }
                }
            }
        }

        if (bestStartX == -1) {
            return false;
        }
        game.move.addmoveDescription(bestStartX, bestStartY, bestEndX, bestEndY, board[bestStartX][bestStartY], board[bestEndX][bestEndY],color);
        if (board[bestEndX][bestEndY]!=null) {
            System.out.println(board[bestEndX][bestEndY].getColor()+" "+color.toLowerCase());
            if (!board[bestEndX][bestEndY].getColor().equals(color.toLowerCase())) {
                game.player2.addCapturedPiece(board[bestEndX][bestEndY].getSymbol());
            }
        }
        Piece selectedPiece = board[bestStartX][bestStartY];
        board[bestStartX][bestStartY] = null;
        selectedPiece.setPosition(bestEndX, bestEndY);
        board[bestEndX][bestEndY] = selectedPiece;

        return true;
    }

    // دالة لتقييم الحركة بناءً على القواعد
    private int evaluateMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        int score = 0;

        // إذا كانت الحركة تلتقط قطعة للخصم
        if (board[endX][endY] != null && !board[endX][endY].getColor().equals(color)) {
            score += 10; // إعطاء أولوية لأخذ قطع الخصم
        }

        // إذا كانت الحركة تقدم القطعة نحو مركز اللوحة
        score += 5 - Math.abs(3 - endX) - Math.abs(3 - endY); // نقاط للمركز

        return score;
    }
}
