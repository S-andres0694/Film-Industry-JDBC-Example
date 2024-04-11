/**
 * Ratings is the class which contains all of the values for the columns of the Ratings table These
 * values are retrieved through a series of methods in the Parsers Class and they are also inputted
 * to the actual database using methods in the PopulateDB class
 *
 * @author 230018374
 */
public class Ratings {
  private final String CriticSource;
  private final double rating;

  /**
   * Constructor that initialises a rating object with values that are going to be put into the
   * Ratings table with values obtained from the parserRatings method
   *
   * @param CriticSource the source that issued the critic. For example: IMDb, Rotten Tomatoes or
   *     Metacritic
   * @param rating the rating the source gave the movie. It is based on a scale from 1 through 5
   */
  public Ratings(String CriticSource, double rating) {
    this.CriticSource = CriticSource;
    this.rating = rating;
  }

  /**
   * Standard getter for the source of the critic
   *
   * @return the source of the critic
   */
  public String getCriticSource() {
    return CriticSource;
  }

  /**
   * Standard getter for the rating of the movie
   *
   * @return the rating of the movie
   */
  public double getRating() {
    return rating;
  }
}
