import java.util.ArrayList;
import java.util.List;

/**
 * Movies is the class which contains all the values for the columns of the Movies table It also
 * contains extra values for the intermediate tables that link the many-to-many relationships. In
 * this case, the awardWinningMovie table, the MoviesWithRatings table and the MoviesWithGenres
 * table These values are retrieved through a series of methods in the Parsers Class, and they are
 * also inputted to the actual database using methods in the PopulateDB class
 *
 * @author 230018374
 */
public class Movies {
  private static int count = 1;
  private final String title;
  private final List<Integer> awardIDs;
  private final List<Integer> ratingsIDs;
  private final String releaseDate;
  private final String runningTime;
  private final List<Integer> genres;
  private final String plotSummary;
  private final int ID;
  private final int director;

  /**
   * Initialises the object containing all the information that is eventually going to be put
   * into the table as an entry. Also initialises the lists containing information for the
   * intermediate tables
   *
   * @param title the movie title
   * @param releaseDate the movie's release date
   * @param runningTime the running time of the movie
   * @param plotSummary the plot of the movie
   * @param director the director of the movie
   */
  public Movies(
      String title, String releaseDate, String runningTime, String plotSummary, int director) {
    this.title = title;
    this.awardIDs = new ArrayList<>();
    this.ratingsIDs = new ArrayList<>();
    this.releaseDate = releaseDate;
    this.runningTime = runningTime;
    this.genres = new ArrayList<>();
    this.plotSummary = plotSummary;
    this.director = director;
    this.ID = count;
    count++;
  }

  /**
   * Standard getter for the AwardIDs that a movie has won
   *
   * @return a list of integer contain the IDs of the awards the movie has won
   */
  public List<Integer> getAwardIDs() {
    return awardIDs;
  }

  /**
   * Standard getter for the director of a movie
   *
   * @return the director's name as a string
   */
  public int getDirectorID() {
    return director;
  }

  /**
   * Standard getter for the GenreIDs that a movie has
   *
   * @return a list of integer contain the IDs of the genres or genre the movie has
   */
  public List<Integer> getGenreIDs() {
    return genres;
  }

  /**
   * Standard getter for the Movie ID
   *
   * @return an integer which is the movieID in the table
   */
  public int getID() {
    return ID;
  }

  /**
   * Standard getter for the movie's plot
   *
   * @return a string with the movie's plot
   */
  public String getPlotSummary() {
    return plotSummary;
  }

  /**
   * Standard getter for the RatingIDs that a movie has
   *
   * @return a list of integer containing the IDs of the ratings the movie has.
   */
  public List<Integer> getRatingsIDs() {
    return ratingsIDs;
  }

  /**
   * Standard getter for the movie's release date
   *
   * @return a string with the movie's release date
   */

  public String getReleaseDate() {
    return releaseDate;
  }

  /**
   * Standard getter for the movie's running time that a movie has
   *
   * @return a list of string with the movie's running time.
   */

  public String getRunningTime() {
    return runningTime;
  }

  /**
   * Standard getter for the movie title
   *
   * @return a string with the movie title
   */

  public String getTitle() {
    return title;
  }

  public static void main(String[] args) throws Exception {

    Parsers obj = new Parsers();
    Movies[] movies = obj.moviesParser(PopulateDB.getMoviesFile());
    for (Movies movie : movies){
      if (movie.getAwardIDs().get(0) > 0) {
        System.out.println(movie.getAwardIDs());
      }

    }

  }
}


