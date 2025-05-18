public class Game {
    
    private String name;
    private String console;
    private String releaseYear;
    private String rating;
    private int numberOfCopies;

    public Game(String name, String console, String releaseYear, String rating, int copies) {
        this.name = name;
        this.console = console;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.numberOfCopies = copies;
    }
}
