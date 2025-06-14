import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Player  {

    private  String name;
    private  String color;
    private List<String> capturedPieces;
    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.capturedPieces = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public void addCapturedPiece(String piece) {
        capturedPieces.add(piece);
    }
    public void clearCapturedPieces() {
        this.capturedPieces.clear();
    }

    public List<String> getCapturedPieces() {
        return capturedPieces;
    }
    public void setCapturedPieces(List<String> capturedPieces) {
        this.capturedPieces = capturedPieces;
    }

    public void displayCapturedPieces() {
        if (capturedPieces.isEmpty()) {
            System.out.println(name + " hasn't captured any pieces yet.");
        } else {
            System.out.println(name + " has captured: " + String.join(", ", capturedPieces));
        }
    }

}