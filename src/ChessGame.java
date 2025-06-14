
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import Piece.*;

public class ChessGame extends JFrame  {

    public static JPanel boardPanel;
    public  Board board;
    public static Piece activePiece;
    public static int activeRow, activeCol;
    public static String currentPlayer;
    public static Player player1, player2;
    public Move move;
    public static boolean withAi=false;
//    private static ArrayList<Move> moveHistory;

    public ChessGame(Player player1, Player player2) {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        this.player1 = player1;
        this.player2 = player2;
        boardPanel = new JPanel(new GridLayout(8, 8));
        board = new Board(boardPanel);
//        moveHistory = new ArrayList<>();
        move=new Move();
        add(boardPanel, BorderLayout.CENTER);
        currentPlayer = "white";

        JButton showMovesButton = new JButton("Show Moves");
        showMovesButton.addActionListener(e -> showMoveHistory());

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> resetGame());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exitGame());

        JButton List1 = new JButton("List Player " + player1.getName());
        List1.addActionListener(e -> list1());

        JButton List2 = new JButton("List Player " + player2.getName());
        List2.addActionListener(e -> list2());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(List1);
        buttonPanel.add(showMovesButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(List2);

        add(buttonPanel, BorderLayout.SOUTH);
        if (!withAi){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ChessGame.this,
                        "Do you want to Save your Progress?",
                        "Exit",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    Savegame();
                    new chessGamemenu();
                    dispose();
                } else if (option == JOptionPane.NO_OPTION) {
                    notSavegame();
                    new chessGamemenu();
                    dispose();
                }
            }

        });
        } else {
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int option = JOptionPane.showConfirmDialog(ChessGame.this,
                            "Do you want to exit",
                            "Exit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (option == JOptionPane.YES_OPTION) {
                        new chessGamemenu();
                        dispose();
                    }
                }
            });

        }
        if (withAi&&currentPlayer.equals("white")&&player2.getColor().toLowerCase().equals("white")){
            Aiplayer(board.grid);
        }
        setVisible(true);
    }

    public ChessGame(String[][]boards,String player11, String player22,String color11,String color22,String currentPlayer,
                     List<String> listplayer1,List<String> listplayer2,List<String> moveHistory) {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        boardPanel = new JPanel(new GridLayout(8, 8));
        this.board = new Board(boardPanel,boards);
        player1=new Player(player11,color11);
        player2=new Player(player22,color22);
        player1.setCapturedPieces(listplayer2);
        player2.setCapturedPieces(listplayer1);

//        this.moveHistory = new ArrayList<Move>();

        move=new Move();
        move.Listload(moveHistory);
        add(boardPanel, BorderLayout.CENTER);
        this.currentPlayer = currentPlayer;

        JButton showMovesButton = new JButton("Show Moves");
        showMovesButton.addActionListener(e -> showMoveHistory());

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> resetGame());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exitGame());

        JButton List1 = new JButton("List Player " + player1.getName());
        List1.addActionListener(e -> list1());

        JButton List2 = new JButton("List Player " + player2.getName());
        List2.addActionListener(e -> list2());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(List1);
        buttonPanel.add(showMovesButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(List2);

        add(buttonPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ChessGame.this,
                        "Do you want to Save your Progress?",
                        "Exit",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    Savegame();
                    new chessGamemenu();
                    dispose();
                } else if (option == JOptionPane.NO_OPTION) {
                    notSavegame();
                    new chessGamemenu();
                    dispose();
                }
            }
        });
        setVisible(true);
    }

    public void Savegame() {
        try {
            System.out.println("save game");
            savegame save=new savegame(board.grid,currentPlayer, player1.getCapturedPieces(), player2.getCapturedPieces(),
                    move.getmoveDescription(),player2.getColor(), player2.getName(), player1.getColor(), player1.getName());
            System.out.println("Game saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public void notSavegame() {
        savegame.notsave();
    }



    public static void startgame() {
        withAi=false;
        if (savegame.checkload()) {
            try {
            System.out.println("Game loaded successfully!");
            savegame.loadboard();

            } catch (Exception e) {
                System.out.println("Error loading game: " + e.getMessage());
                PlayerSelectionScreen.showPlayerSelectionScreen();
                player1 = PlayerSelectionScreen.getPlayer1();
                player2 = PlayerSelectionScreen.getPlayer2();
                new ChessGame(player1, player2);
            }
        } else {
            PlayerSelectionScreen.showPlayerSelectionScreen();
            player1 = PlayerSelectionScreen.getPlayer1();
            player2 = PlayerSelectionScreen.getPlayer2();
            new ChessGame(player1, player2);
        }
    }

    public static void startgameAi() {
//        if (loadornot()) {
//            try {
//                System.out.println("Game loaded successfully!");
//                savegame.loadboard();
//
//            } catch (Exception e) {
//                System.out.println("Error loading game: " + e.getMessage());
//                PlayerSelectionScreen.showPlayerSelectionScreenAi();
//                player1 = PlayerSelectionScreen.getPlayer1();
//                player2 = PlayerSelectionScreen.getPlayer2();
//                new ChessGame(player1, player2);
//            }
//        } else {
            PlayerSelectionScreen.showPlayerSelectionScreenAi();
            player1 = PlayerSelectionScreen.getPlayer1();
            player2 = PlayerSelectionScreen.getPlayer2();
            withAi=true;
            new ChessGame(player1, player2);
//        }
    }

    private void resetGame() {
        board.resetBoard();
//        moveHistory.clear();
        currentPlayer = "white";
        board.updateBoardUI();
        player1.clearCapturedPieces();
        player2.clearCapturedPieces();
        move.clearmoveDescription();
    }

    private void exitGame() {
        if (!withAi) {
            int option = JOptionPane.showConfirmDialog(ChessGame.this,
                    "Do you want to Save your Progress?",
                    "Exit",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                System.out.println("save game");
                Savegame();
                new chessGamemenu();
                dispose();
            } else if (option == JOptionPane.NO_OPTION) {
                System.out.println("not save");
                notSavegame();
                new chessGamemenu();
                dispose();
            }
        }else{
            int option = JOptionPane.showConfirmDialog(ChessGame.this,
                    "Do you want to exit",
                    "Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                new chessGamemenu();
                dispose();
            }
        }
    }

    public void handleSquareClick(int row, int col) {
        Piece[][] grid = board.getGrid();
        System.out.println(row + " " + col);
        if (activePiece == null) {
            activePiece = grid[row][col];
            if (activePiece == null || !activePiece.getColor().equals(currentPlayer)) {
                activePiece = null;
                return;
            }
//            System.out.println("first click");
            activeRow = row;
            activeCol = col;
            board.updateBoardUI();
        } else {

//            System.out.println("scond click");

            if (isValidMove(activeRow, activeCol, row, col)) {
                if (activePiece.isValidMove(activeRow, activeCol, row, col, grid)) {
//                    System.out.println(" "+activeRow+" "+activeCol+row+" "+col);
//                  Move move = new Move(activeRow, activeCol, row, col, activePiece, grid[row][col],currentPlayer);
                    move.addmoveDescription(activeRow, activeCol, row, col, activePiece, grid[row][col],currentPlayer);
                    if (grid[row][col]!=null) {
                        if (grid[row][col].getColor().equals(player2.getColor().toLowerCase())) {
                            player1.addCapturedPiece(grid[row][col].getSymbol());
                        } else if (grid[row][col].getColor().equals(player1.getColor().toLowerCase())) {
                            player2.addCapturedPiece(grid[row][col].getSymbol());
                        }
                    }
//                    Savegame(this);
                    grid[row][col] = activePiece;
                    grid[activeRow][activeCol] = null;

                    if (activePiece instanceof Pawn) {
                        Pawn.en_passant(activeRow,activeCol,row,col,grid,currentPlayer);
                        Pawn.pawn_promotion(row,col,grid,currentPlayer,activePiece);
                    }
                    if (activePiece instanceof King) {
                        King.castling(activeRow, activeCol, row, col, grid,activePiece);
                    }
                    if (activePiece instanceof Rook) {
                        ((Rook) activePiece).firstMove=false;
                    }
                    activePiece = null;
                    currentPlayer = currentPlayer.equals("white") ? "black" : "white";


                    if ((player2.getColor().toLowerCase()).equals(currentPlayer)&&withAi){
                        Aiplayer(grid);
                    }
                    else {
                        board.updateBoardUI();
                        checkDrawConditions();
                    }
                }
                else {
                    activePiece = null;
                }
            } else {
                activePiece = null;
            }
        }
    }

    public void Aiplayer(Piece[][] grid){
//        System.out.println("Ai plays");
        AIPlayer ai=new AIPlayer(currentPlayer,this);
        ai.makeMove(grid);
        board.updateBoardUI();
        checkDrawConditions();
        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
    }

    public boolean isValidMove(int startX, int startY, int endX, int endY) {
        Piece[][] grid = board.getGrid();

        if (startX < 0 || startX >= 8 || startY < 0 || startY >= 8 ||
                endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            return false;
        }

//        if (startX == endX && startY == endY) {
//            return false;
//        }

        Piece piece = grid[startX][startY];
        if (piece == null) {
            return false;
        }

        Piece targetPiece = grid[endX][endY];
        if (targetPiece != null && targetPiece.getColor().equals(piece.getColor())) {
            return false;
        }

        Piece tempPiece = grid[endX][endY];
        grid[endX][endY] = piece;
        grid[startX][startY] = null;

        boolean isCheck = isInCheck(piece.getColor());

        grid[startX][startY] = piece;
        grid[endX][endY] = tempPiece;

        return !isCheck;
    }

    public boolean isInCheck(String playerColor) {
        Piece[][] grid = board.getGrid();
        int kingX = -1, kingY = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece instanceof King && piece.getColor().equals(playerColor)) {
                    kingX = row;
                    kingY = col;
                    break;
                }
            }
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece opponentPiece = grid[row][col];
                if (opponentPiece != null && !opponentPiece.getColor().equals(playerColor)) {
                    if (opponentPiece.isValidMove(row, col, kingX, kingY, grid)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCheckmate(String playerColor) {

        if (!isInCheck(playerColor)) {
            return false;
        }
        System.out.println(playerColor);
        Piece[][] grid = board.getGrid();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null && piece.getColor().equals(playerColor)) {

                    for (int destRow = 0; destRow < 8; destRow++) {
                        for (int destCol = 0; destCol < 8; destCol++) {
                            if (isValidMove(row, col, destRow, destCol))
                                if (piece.isValidMove(row, col, destRow, destCol, grid)) {
                                    Piece tempPiece = grid[destRow][destCol];
                                    grid[destRow][destCol] = piece;
                                    grid[row][col] = null;
                                    if (!isInCheck(playerColor)) {
                                        grid[row][col] = piece;
                                        grid[destRow][destCol] = tempPiece;
                                        System.out.println(row + " " + col + " " + destRow + " " + destCol);
                                        return false;
                                    }
                                    grid[row][col] = piece;
                                    grid[destRow][destCol] = tempPiece;
                                    System.out.println(row + " " + col + " " + destRow + " " + destCol);
                                }
                        }
                    }

                }
            }
        }
        return true;
    }

    public void checkDrawConditions() {
        if (isCheckmate(currentPlayer)) {
            String winner = (currentPlayer.equals("white")) ? "Black" : "White";
            JOptionPane.showMessageDialog(this, winner + " wins! Game Over.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            notSavegame();
            new chessGamemenu();
            dispose();
        }
        else if (isStalemate(currentPlayer)) {
            JOptionPane.showMessageDialog(this, "Stalemate! The game is a draw.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            notSavegame();
            new chessGamemenu();
            dispose();
        } else if (isInsufficientMaterial()) {
            JOptionPane.showMessageDialog(this, "Insufficient material! The game is a draw.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            notSavegame();
            new chessGamemenu();
            dispose();
        }
        else if (Move.threefoldrepetition)
        {
            JOptionPane.showMessageDialog(this, "Threefold repetition! The game is a draw.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            notSavegame();
            new chessGamemenu();
            dispose();
        }
        else if (Move.checka50move)
        {
            JOptionPane.showMessageDialog(this, "A 50-move rule! The game is a draw.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            notSavegame();
            new chessGamemenu();
            dispose();
        }
    }

    private boolean isStalemate(String playerColor) {
        if (isInCheck(playerColor)) {
            return false;
        }
        Piece[][] grid = board.getGrid();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null && piece.getColor().equals(playerColor)) {
                    for (int destRow = 0; destRow < 8; destRow++) {
                        for (int destCol = 0; destCol < 8; destCol++) {
                            if (isValidMove(row, col, destRow, destCol) ){
                                if (piece.isValidMove(row, col, destRow, destCol, grid)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isInsufficientMaterial() {
        Piece[][] grid = board.getGrid();
        int whitePieces = 0, blackPieces = 0;
        boolean thereisbishopwhite = true, thereisknightwhite = true;
        boolean thereisbishopblack = true, thereisknightblack = true;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null) {
                    if (piece.getColor().equals("white")) {
                        whitePieces++;
                        if (piece instanceof King) {
                            whitePieces--;
                        }
                        else if (piece instanceof Bishop && thereisbishopwhite && thereisknightwhite) {
                            whitePieces--;
                            thereisbishopwhite = false;
                        }
                        else if (piece instanceof Knight && thereisbishopwhite && thereisknightwhite) {
                            whitePieces--;
                            thereisknightwhite = false;
                        }
                    } else {
                        blackPieces++;
                        if (piece instanceof King) {
                            blackPieces--;
                        }
                        else if (piece instanceof Bishop && thereisbishopblack && thereisknightblack) {
                            blackPieces--;
                            thereisbishopblack = false;
                        }
                        else if (piece instanceof Knight && thereisbishopblack && thereisknightblack) {
                            blackPieces--;
                            thereisknightblack = false;
                        }
                    }
                }
            }
        }
        System.out.println(whitePieces+" "+blackPieces);
        return (whitePieces == 0 && blackPieces == 0) ;
    }


    private void list1() {

        StringBuilder Listplayer1 = new StringBuilder("list of captured pieces:\n");
        for (String player : player1.getCapturedPieces()) {
            Listplayer1.append(player).append("\n");
        }
        JTextArea textArea = new JTextArea(Listplayer1.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Player: "+player1.getName(), JOptionPane.PLAIN_MESSAGE);
    }

    private void list2() {

        StringBuilder Listplayer2 = new StringBuilder("list of captured pieces:\n");
        for (String player : player2.getCapturedPieces()) {
            Listplayer2.append(player).append("\n");
        }
        JTextArea textArea = new JTextArea(Listplayer2.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Player: "+player2.getName(), JOptionPane.PLAIN_MESSAGE);
    }

    private void showMoveHistory() {
        StringBuilder history = new StringBuilder("Move History:\n");
        for (String move1 : move.getmoveDescription()) {
            history.append(move1).append("\n");
        }

        JTextArea textArea = new JTextArea(history.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Move History", JOptionPane.PLAIN_MESSAGE);
    }

}
