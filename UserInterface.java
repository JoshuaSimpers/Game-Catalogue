import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import java.util.ArrayList;

public class UserInterface {
    
    private File mainFile;
    private File mainFileCopy;
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Game> gameList = new ArrayList<>();

    public UserInterface() {

    }

    public void start() {
        System.out.println("\n == Video Game Catalogue v0.6.0 by Joshua Simpers == \n");
        createFile();
        readFile();
        readCommands();
    }

    public void createFile() {
        try {
            mainFile = new File("cat.csv");
            if (mainFile.createNewFile()) {
                System.out.println(mainFile.getName() + " created!");
            } else {
                System.out.println(mainFile.getName() + " already exists. \n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void createFileCopy() {
        try {
            mainFileCopy = new File("catCopy.csv");
            if (mainFileCopy.createNewFile()) {
                System.out.println(mainFileCopy.getName() + " created!");
            } else {
                System.out.println(mainFileCopy.getName() + " already exists. \n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void deleteOldFileAndRenameCopy(File oldFile, File copy) {
        oldFile.delete();
        Path source = copy.toPath();
        try {
            Files.move(source, source.resolveSibling("cat.csv"), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
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
                    data = santitizeString(data);
                }
                gameList.add(new Game(gameData[0], gameData[1], gameData[2], gameData[3], Integer.valueOf(gameData[4])));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToFile(String targetFile, String name, String console, String releaseYear, String rating, int copies) {
        try {
            BufferedWriter catalogueWriter = new BufferedWriter(new FileWriter(targetFile, true));
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
        System.out.println("== List - Lists all games in the catalogue ==");
        System.out.println("== Add - Adds a game to the catalogue ==");
        System.out.println("== Exit - Exits the application ==");
        System.out.println("== Remove - Removes a game from the list ==");
        System.out.println("== Find - Finds games based on the user criteria ==");
    }

    public void readCommands() {
        while (true) {
            listCommands();
            System.out.println("Command: ");
            String input = scanner.nextLine();
            input = santitizeString(input);
            if (input.equals("exit")) {
                System.out.println("Exiting program. Goodbye!");
                break;
            }
            if (input.equals("list")) {
                listGamesInCatalogue();
            } 
            if (input.equals("add")) {
                addGameToCatalogue();
            }
            if (input.equals("remove")) {
                removeGameFromCatalogue();
            } 
            if (input.equals("find")) {
                readFindCommands();
            }else {
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
        Game targetGame;
        System.out.println("Name: ");
        String name = readInput();
        name = santitizeString(name);
        System.out.println("Platform: ");
        String platform = readInput();
        platform = santitizeString(platform);
        targetGame = gameFound(name, platform);
        if (gameFound(name, platform) != null) {
            System.out.println("Game already exists! Increasing the amount of copies of the game by 1!");
            targetGame.increaseCopies(1);
            createFileCopy();
            for (Game game : gameList) {
                writeToFile("catCopy.csv", game.getName(), game.getConsole(), game.getYear(), game.getRating(), game.getCopies());
            }
            deleteOldFileAndRenameCopy(mainFile, mainFileCopy);
        } else {
            System.out.println("Year: ");
            String year = readInput();
            System.out.println("Rating: ");
            String rating = readInput();
            System.out.println("Number of Copies: ");
            String copies = readInput();
            Integer copiesToInt = Integer.valueOf(copies);
            gameList.add(new Game(name, platform, year, rating, copiesToInt));
            writeToFile("cat.csv" ,name, platform, year, rating, copiesToInt);
        }
    }

    public String readInput() {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            System.out.println("Invalid value! Cancelling operation!");
            readCommands();
        }
        return input;
    }

    public Game gameFound(String name, String platform) {
        for (Game game : gameList) {
            String santizedName = game.getName();
            String sanitizedPlatform = game.getConsole();
            santizedName = santitizeString(santizedName);
            sanitizedPlatform = santitizeString(sanitizedPlatform);
            if (santizedName.equals(name) && sanitizedPlatform.equals(platform)) {
                return game;
            }
        }
        return null;
    }

    public void removeGame(String name, String platform) {
        Game targetGame = gameFound(name, platform);
        if (targetGame.getCopies() > 1) {
            System.out.println("There are multiple copies. How many copies would you like to remove from the catalogue?");
            Integer amount = Integer.valueOf(readInput());
            targetGame.decreaseCopies(amount);
            System.out.println("Amount of copies successfully reduced by " + amount + "!");
            createFileCopy();
            for (Game game : gameList) {
                writeToFile("catCopy.csv", game.getName(), game.getConsole(), game.getYear(), game.getRating(), game.getCopies());
            }
            deleteOldFileAndRenameCopy(mainFile, mainFileCopy);
        }
        else {
            createFileCopy();
            for (Game game : gameList) {
                if (!game.equals(targetGame)) {
                    writeToFile("catCopy.csv", game.getName(), game.getConsole(), game.getYear(), game.getRating(), game.getCopies());
                }
            }
            gameList.remove(targetGame);
            deleteOldFileAndRenameCopy(mainFile, mainFileCopy);
        }

    }

    public void removeGameFromCatalogue() {
        System.out.println("What game would you like to remove?");
        System.out.println("Name: ");
        String name = readInput();
        System.out.println("Platform: ");
        String platform = readInput();
        name = santitizeString(name);
        platform = santitizeString(platform);
        System.out.println(name);
        System.out.println(platform);
        if (gameFound(name, platform) == null) {
            System.out.println("Game not found! Cancelling the operation!");    
        } else {
            removeGame(name, platform);
            System.out.println("Game successfully removed!");
        }
    }

    public void readFindCommands() {
        while (true) {
            listFindCommands();
            String findCommand = scanner.nextLine();
            findCommand = santitizeString(findCommand);
            if (findCommand.equals("cancel")) {
                readCommands();
            } else if (!findCommand.equals("year") && !findCommand.equals("platform") && !findCommand.equals("rating") && !findCommand.equals("name")) {
                System.out.println("Invalid command! Try again!");
                continue;
            } else {
                findGames(findCommand);
            }
            break;
        }
    }

    public void listFindCommands() {
        System.out.println("== Year - Finds games based on the desired yea r==");
        System.out.println("== Platform - Finds games based on the platform of release ==");
        System.out.println("== Rating - Finds games by their ESRB rating ==");
        System.out.println("== Name - Finds games that match the given name ==");
        System.out.println("== Cancel - returns to the previous menu ==");
        System.out.println("Command:");
    }

    public void findGames(String type) {
        System.out.println("Enter the desired " + type + ":");
        String desiredType = readInput();
        desiredType = santitizeString(desiredType);
        ArrayList<Game> targetGamesList = new ArrayList<>();
        for (Game nextGame : gameList) {
            String currentTargetType = returnData(type, nextGame);
            currentTargetType = santitizeString(currentTargetType);
            if (currentTargetType.contains(desiredType)) {
                targetGamesList.add(nextGame);
            }
        }
        if (targetGamesList.isEmpty()) {
            System.out.println("Found 0 games that match.");
        } else {
            System.out.println("Found: " + targetGamesList.size() + " game(s) that match:");
            for (Game groupedGame : targetGamesList) {
                System.out.println(groupedGame.toString());
            }
        }
    }

    public String returnData(String targetData, Game currentGame) {
        if (targetData.equals("year")) {
            return currentGame.getYear();
        } else if (targetData.equals("platform")) {
            return currentGame.getConsole();
        } else if (targetData.equals("name")) {
            return currentGame.getName();
        } else if (targetData.equals("rating")) {
            return currentGame.getRating();
        }
        return null;
    }

    public String santitizeString(String stringToBeSanitized) {
        return stringToBeSanitized.trim().toLowerCase();
    }
}
