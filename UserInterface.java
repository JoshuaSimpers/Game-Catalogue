import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UserInterface {
    
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Game> gameList = new ArrayList<>();
    private Integer exitCondition = 0;

    public UserInterface() {

    }

    public void start() {
        System.out.println("==Video Game Catalogue v0.2.0 by Joshua Simpers==");
        createFile();
        readFile();
        readCommands();
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
                String[] gameData = nextLineInFile.split(", ");
                for (String data : gameData) {
                    data.toLowerCase();
                    data.trim();
                }
                gameList.add(new Game(gameData[0], gameData[1], gameData[2], gameData[3], Integer.valueOf(gameData[4])));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToFile(String name, String console, String releaseYear, String rating, int copies) {
        try {
            BufferedWriter catalogueWriter = new BufferedWriter(new FileWriter("cat.csv", true));
            catalogueWriter.write(name + ", " + console + ", " + releaseYear + ", " + rating + ", " + copies);
            catalogueWriter.newLine();
            catalogueWriter.close();
            System.out.println("Game added to catalogue!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void listCommands() {
        System.out.println("==List - Lists all games in the catalogue==");
        System.out.println("==Add - Adds a game to the catalogue==");
        System.out.println("==Exit - Exits the application==");
    }

    public void readCommands() {
        while (true) {
            listCommands();
            System.out.println("Command: ");
            String input = scanner.nextLine();
            input.trim();
            input.toLowerCase();
            if (input.equals("exit")) {
                System.out.println("Exiting program. Goodbye!");
                break;
            }
            if (input.equals("list")) {
                listGamesInCatalogue();
            } 
            if (input.equals("add")) {
                addGameToCatalogue();
            } else {
                continue;
            }
        }
    }

    public void listGamesInCatalogue() {
        for (Game game : this.gameList) {
            System.out.println(game.toString());
        }
    }

    public void addGameToCatalogue() {
        System.out.println("Name: ");
        String name = readInput();
        System.out.println("Platform: ");
        String platform = readInput();
        System.out.println("Year: ");
        String year = readInput();
        System.out.println("Rating: ");
        String rating = readInput();
        System.out.println("Number of Copies: ");
        String copies = readInput();
        Integer copiesToInt = Integer.valueOf(copies);
        gameList.add(new Game(name, platform, year, rating, copiesToInt));
        writeToFile(name, platform, year, rating, copiesToInt);
    }

    public String readInput() {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            System.out.println("Invalid value! Game could not be added to catalogue!");
            readCommands();
        }
        return input;
    }

    public boolean stringIsEmpty(String input) {
        return input.isEmpty();
    }
}
