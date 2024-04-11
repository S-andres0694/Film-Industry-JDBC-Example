import java.util.ArrayList;
import java.util.List;

/**
 * Director is the class which contains all the values for the columns of the Directors table It
 * also contains extra values for the intermediate tables that link the many-to-many relationships,
 * which in this case is the awardWinningDirector table. These values are retrieved through a series
 * of methods in the Parsers Class, and they are also inputted to the actual database using methods
 * in the PopulateDB class
 *
 * @author 230018374
 */
public class Director {
  private static int count = 1;
  private final String name;
  private final int movieCount;
  private final int ID;
  private final List<Integer> awardIDs;

  /**
   * Creates the director object which contains values that will turn into the correspondent entries
   * in the tables Also initialises the awardIDs arrayList, which is going to be used to store the
   * values for its intermediate table AwardWinningDirector
   *
   * @param name the name of the director
   * @param movieCount the amount of movies the director has worked in
   */
  public Director(String name, int movieCount) {
    this.name = name;
    this.movieCount = movieCount;
    this.ID = count;
    this.awardIDs = new ArrayList<>();
    count++;
  }

  /**
   * Standard getter for the IDs of the awards that the director has won
   *
   * @return the IDs of the awards that the director has won in the form of a list of integers
   */
  public List<Integer> getAwardIDs() {
    return awardIDs;
  }

  /**
   * Standard getter for the IDs of the director on the table
   *
   * @return the ID of the director on the table as an integer
   */
  public int getID() {
    return ID;
  }

  /**
   * Standard getter for the movie count of the director
   *
   * @return the movie count of the director as an integer
   */
  public int getMovieCount() {
    return movieCount;
  }

  /**
   * Standard getter for the name of the director
   *
   * @return the name of the director as a string
   */
  public String getName() {
    return name;
  }
}
