import java.io.*;

public class FileManager {

    public static void saveToFile(String filename, savegame game) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(game);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Error saving the game!");
        }
    }

    public static savegame loadToFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            savegame loadedGame = (savegame) ois.readObject();
            System.out.println("done the loading the game!");
            return loadedGame;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Error loading the game!");
            return null;
        }
    }
}
