import Piece.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Move  {
    private  int startRow;
    private  int startCol;
    private  int endRow;
    private  int endCol;
    private Piece movedPiece;
    private Piece capturedPiece;
    private static int countmove_drawW;private static int countmove_drawB;
    private static int startRow2W=0;private static int startCol2W=0;private static int endRow2W=0;private static int endCol2W=0;
    private static int startRow2B=0;private static int startCol2B=0;private static int endRow2B=0;private static int endCol2B=0;
    public static boolean threefoldrepetition =false;
    private static int a50move=50;
    public static boolean checka50move=false;
    private static List<String>moveHistory;

//    public Move(int startRow, int startCol, int endRow, int endCol, Piece movedPiece, Piece capturedPiece,String color) {
//        this.startRow = startRow;
//        this.startCol = startCol;
//        this.endRow = endRow;
//        this.endCol = endCol;
//        this.movedPiece = movedPiece;
//        this.capturedPiece = capturedPiece;
//        threefoldrepetition = threefold_repetition(color);
//        checka50move=a50_move();
//    }

//    @Override
//    public String toString() {
//        String moveDescription = movedPiece.getSymbol() + " moved from (" + startRow + ", " + startCol + ") to ("
//                + endRow + ", " + endCol + ")";
//        if (capturedPiece != null) {
//            moveDescription += ", capturing " + capturedPiece.getSymbol();
//            if (capturedPiece.getColor().equals("white")) {
//
//            }
//        }
//
//        return moveDescription;
//    }


    //    public String toString() {
//        String moveDescription = movedPiece.getSymbol() + ": " + moves(this.startRow, this.startCol) + " to " + moves(this.endRow, this.endCol);
//        if (capturedPiece != null) {
//            moveDescription = movedPiece.getSymbol() + ": " + moves(this.startRow, this.startCol) + " to x " + moves(this.endRow, this.endCol);
//        }
//
//        return moveDescription;
//    }

    public Move()
    {
        this.moveHistory = new ArrayList<>();
    }

    public  boolean threefold_repetition(String color) {
        boolean check = color.equals("white")? true : false ;
        if (check) {
            if (startCol == endCol2W && startRow == endRow2W && endRow == startRow2W && endCol == startCol2W)
            {
                countmove_drawW++;
            }
            else {

                countmove_drawW=0;
                countmove_drawB=0;
            }
            startCol2W=startCol;startRow2W=startRow;endCol2W=endCol;endRow2W=endRow;
        }
        else
        {
            if (startCol==endCol2B && startRow==endRow2B&&endRow==startRow2B&&endCol==startCol2B)
            {
                countmove_drawB++;
            }
            else {

                countmove_drawW=0;
                countmove_drawB=0;
            }
            startCol2B=startCol;startRow2B=startRow;endCol2B=endCol;endRow2B=endRow;
        }
//        System.out.println(countmove_drawW+" "+countmove_drawB);
        if (countmove_drawB==3 && countmove_drawW==3)
        {
            startRow2B=0;startCol2B=0;endRow2B=0;endCol2B=0;
            startRow2W=0;startCol2W=0;endRow2W=0;endCol2W=0;
            countmove_drawB=0;countmove_drawW=0;
            return true;
        }
        return false;
    }

    public boolean a50_move() {
        if (capturedPiece == null && !(movedPiece instanceof Pawn))
        {
            a50move--;
        }
        else{
            a50move=50;
        }
//        System.out.println(a50move);
        if (a50move == 0) {
            a50move=50;
            return true;
        }
        return false;
    }

    public String moves(int endRow, int endCol ) {

        endRow = 8 - endRow;
        String col = new String();
        switch (endCol){
            case 0: col = "a"; break;
            case 1: col = "b"; break;
            case 2: col = "c"; break;
            case 3: col = "d"; break;
            case 4: col = "e"; break;
            case 5: col = "f"; break;
            case 6: col = "g"; break;
            case 7: col = "h"; break;
        }
        String move = (String)col +endRow;
        return move;
    };

    public void addmoveDescription(int startRow, int startCol, int endRow, int endCol, Piece movedPiece, Piece capturedPiece,String color) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        threefoldrepetition = threefold_repetition(color);
        checka50move=a50_move();
        String moveDescription = movedPiece.getSymbol() + ": " + moves(this.startRow, this.startCol) + " to " + moves(this.endRow, this.endCol);
        if (capturedPiece != null) {
            moveDescription = movedPiece.getSymbol() + ": " + moves(this.startRow, this.startCol) + " to x " + moves(this.endRow, this.endCol);
        }
        moveHistory.add(moveDescription);
    }

    public void clearmoveDescription() {
        this.moveHistory.clear();
    }

    public List<String> getmoveDescription() {
        return moveHistory;
    }

    public void Listload(List<String> moveHistory) {
        this.moveHistory = moveHistory;
    }

}


