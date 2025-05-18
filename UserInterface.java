import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UserInterface {
    
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Game> gameList = new ArrayList<>();

    public UserInterface() {

    }

    public void start() {
        System.out.println("==Video Game Catalogue v0.1 by Joshua Simpers==");
        createFile();
        readFile();
    }

    public void createFile() {
        try {
            File fileObj = new File("cat.csv");
            if (fileObj.createNewFile()) {
                System.out.println(fileObj.getName() + " created!");
            } else {
                System.out.println(fileObj.getName() + " already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void readFile() {
        try (Scanner fileScanner = new Scanner(Paths.get("cat.csv"))){
            while (fileScanner.hasNextLine()) {
                String nextLineInFile = fileScanner.nextLine();
                String[] gameData = nextLineInFile.split(",");
                for (String data : gameData) {
                    data.toLowerCase();
                    data.trim();
                }
                gameList.add(new Game(gameData[0], gameData[1], gameData[2], gameData[3], Integer.valueOf(gameData[4])));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void listCommands() {
        System.out.println("==List - Lists all games in the catalogue==");
        System.out.println("==Add - Adds a game to the catalogue==");
        System.out.println("==Remove - Removes a game from the catalogue that matches the user's description==");
        System.out.println("==Find - Finds a game based on the user's description==");
    }

    public void readCommands() {
        
    }
}
