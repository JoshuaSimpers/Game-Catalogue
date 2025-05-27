public class Game implements Comparable<Game> {
    
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

    public String getName() {
        return this.name;
    }

    public String getConsole() {
        return this.console;
    }

    public String getYear() {
        return this.releaseYear;
    }

    public String getRating() {
        return this.rating;
    }

    public Integer getCopies() {
        return this.numberOfCopies;
    }

    public void decreaseCopies(int amount) {
        this.numberOfCopies = this.numberOfCopies - amount;
    }

    public void increaseCopies(int amount) {
        this.numberOfCopies += amount;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Platform: " + getConsole() + ", Year: " + getYear() + ", Rating: " + getRating() + ", Number of Copies: " + getCopies();
    }

    @Override
    public int compareTo(Game comparedGame){
        return -1;
    }
}
