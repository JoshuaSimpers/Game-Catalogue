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

    public UserInterface() {

    }

    public void start() {
        System.out.println("==Video Game Catalogue v0.1 by Joshua Simpers==");
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
            // TODO: handle exception
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
        while (true) {
            System.out.println("Name: ");
            String inputName = scanner.nextLine();
            if (inputName.isEmpty()) {
                System.out.println("Nothing was entered. Cancelling...");
                break;
            }
            String name = inputName;
            System.out.println("Platform: ");
            String inputPlatform = scanner.nextLine();
            if (inputPlatform.isEmpty()) {
                System.out.println("Nothing was entered. Cancelling...");
                break;
            }
            String platform = inputPlatform;
            System.out.println("Year: ");
            String inputYear = scanner.nextLine();
            if (inputYear.isEmpty()) {
                System.out.println("Nothing was entered. Cancelling...");
                break;
            }
            String year = inputYear;
            System.out.println("Rating: ");
            String inputRating = scanner.nextLine();
            if (inputRating.isEmpty()) {
                System.out.println("Nothing was entered. Cancelling...");
                break;
            }
            String rating = inputRating;
            System.out.println("Number of Copies: ");
            String inputCopies = scanner.nextLine();
            if (inputCopies.isEmpty()) {
                System.out.println("Nothing was entered. Cancelling...");
                break;
            }
            Integer copies = Integer.valueOf(inputCopies);
            gameList.add(new Game(name, platform, year, rating, copies));
            writeToFile(name, platform, year, rating, copies);
            break;
        }
    }
}
