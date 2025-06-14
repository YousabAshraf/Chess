import Piece.*;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Board  {
    Piece grid[][];
    JPanel boardPanel;

    public Board(JPanel boardPanel) {
        this.boardPanel = boardPanel;
        grid = new Piece[8][8];
        initializeBoard();
    }

    public Board(JPanel boardPanel,String[][] name){
        this.boardPanel = boardPanel;
        grid = new Piece[8][8];
        initializeBoardload(name);
        updateBoardUI();
    }

    public void resetBoard() {
        grid = new Piece[8][8];
        initializeBoard();
    }

    public Piece[][] getGrid() {
        return grid;
    }

    private void initializeBoardload(String[][] name){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (name[i][j]) {
                    case "Pawn_white":
                        grid[i][j] = new Pawn("white", j, i);
                        break;
                    case "Pawn_black":
                        grid[i][j] = new Pawn("black", j, i);
                        break;
                    case "Queen_white":
                        grid[i][j] = new Queen("white", j, i);
                        break;
                    case "Queen_black":
                        grid[i][j] = new Queen("black", j, i);
                        break;
                    case "King_white":
                        grid[i][j] = new King("white", j, i);
                        break;
                    case "King_black":
                        grid[i][j] = new King("black", j, i);
                        break;
                    case "Bishop_white":
                        grid[i][j] = new Bishop("white", j, i);
                        break;
                    case "Bishop_black":
                        grid[i][j] = new Bishop("black", j, i);
                        break;
                    case "Knight_white":
                        grid[i][j] = new Knight("white", j, i);
                        break;
                    case "Knight_black":
                        grid[i][j] = new Knight("black", j, i);
                        break;
                    case "Rook_white":
                        grid[i][j] = new Rook("white", j, i);
                        break;
                    case "Rook_black":
                        grid[i][j] = new Rook("black", j, i);
                        break;
                    default:
                        grid[i][j] = null;

                }
            }
        }
    }


    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new Pawn("black", 1, i);
            grid[6][i] = new Pawn("white", 6, i);
        }

        grid[0][0] = new Rook("black", 0, 0);
        grid[0][1] = new Knight("black", 0, 1);
        grid[0][2] = new Bishop("black", 0, 2);
        grid[0][3] = new Queen("black", 0, 3);
        grid[0][4] = new King("black", 0, 4);
        grid[0][5] = new Bishop("black", 0, 5);
        grid[0][6] = new Knight("black", 0, 6);
        grid[0][7] = new Rook("black", 0, 7);

        grid[7][0] = new Rook("white", 7, 0);
        grid[7][1] = new Knight("white", 7, 1);
        grid[7][2] = new Bishop("white", 7, 2);
        grid[7][3] = new Queen("white", 7, 3);
        grid[7][4] = new King("white", 7, 4);
        grid[7][5] = new Bishop("white", 7, 5);
        grid[7][6] = new Knight("white", 7, 6);
        grid[7][7] = new Rook("white", 7, 7);

        updateBoardUI();
    }

    public void updateBoardUI() {
        try {
            boardPanel.removeAll();

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    JButton square = new JButton();
                    square.setFont(new Font("Arial", Font.BOLD, 18));

                    square.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);

                    int finalRow = row;
                    int finalCol = col;
                    square.addActionListener(e -> {
                        ChessGame game = (ChessGame) SwingUtilities.getWindowAncestor(boardPanel);
                        game.handleSquareClick(finalRow, finalCol);
                    });

                    if (grid[row][col] != null) {
                        ImageIcon pieceIcon = getPieceIcon(grid[row][col]);
                        if (pieceIcon != null) {
                            square.setIcon(pieceIcon);
                        }
                    }

                    boardPanel.add(square);
                }
            }

            boardPanel.revalidate();
            boardPanel.repaint();
        } catch (Exception e) {
            System.err.println("Exception updating board UI: " + e.getMessage());
        }
    }

    private ImageIcon getPieceIcon(Piece piece) {
        String pieceName = piece.getClass().getSimpleName().toLowerCase();
        String color = piece.getColor().toLowerCase();
        String filePath = "images/" + color + "_" + pieceName + ".png";
        try {
            return new ImageIcon(getClass().getResource(filePath));
        } catch (Exception e) {
            System.err.println("Error loading image: " + filePath);
            return null;
        }
    }

}