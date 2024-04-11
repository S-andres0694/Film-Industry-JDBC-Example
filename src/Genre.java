/**
 * Genre is the class which contains all the values for the columns of the Genre table These
 * values are retrieved through a series of methods in the Parsers Class, and they are also inputted
 * to the actual database using methods in the PopulateDB class
 *
 * @author 230018374
 */
public class Genre {
  private final String genreName;

  /**
   * Creates the genre object, which stores a name. This is later going to become an entry on the
   * table
   *
   * @param genreName name of the genre. Ex. Action, war, etc
   */
  public Genre(String genreName) {
    this.genreName = genreName;
  }

  /**
   * Standard getter for the name of the genre
   *
   * @return the name of the genre as a string
   */
  public String getGenreName() {
    return genreName;
  }
}
