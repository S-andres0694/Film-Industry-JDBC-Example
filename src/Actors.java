import java.util.ArrayList;
import java.util.List;

/**
 * Actors is the class which contains all the values for the columns of the Actors table. It also
 * contains extra values for the intermediate tables that link the many-to-many relationships, which
 * in this case are the Cast and the awardWinningActor tables. These values are retrieved through a
 * series of methods in the Parsers class, and they are also inputted to the actual database using
 * methods in the PopulateDB class
 *
 * @author 230018374
 */
public class Actors {
  private static int amountOfActors = 1;
  private final String name;
  private final String birthday;
  private final int age;
  private final int id;
  private final List<Integer> awardIDs;
  private final List<Integer> movieIDs;

  /**
   * Initialises each one of the actor objects, which correspond to entries on the table. It also
   * initialises ArrayList for the awardIDs and movieIDs, which are later used to fill data for
   * other intermediate tables.
   *
   * @param name the actors name
   * @param age the actors age
   * @param birthday the actors birthday
   */
  public Actors(String name, int age, String birthday) {
    this.name = name;
    this.age = age;
    this.birthday = birthday;
    this.awardIDs = new ArrayList<>();
    this.movieIDs = new ArrayList<>();
    this.id = amountOfActors;
    amountOfActors++;
  }

  /**
   * Standard getter for the Actor's age
   *
   * @return the age of the actor in an integer
   */
  public int getAge() {
    return age;
  }

  /**
   * Standard getter for the awardIDs of the actor (if he has won awards)
   *
   * @return the IDs of the awards that the actor might have won, in the form of a List
   */
  public List<Integer> getAwardIDs() {
    return awardIDs;
  }

  /**
   * Standard getter for the Actor's birthday
   *
   * @return the birthday of the actor as a String
   */
  public String getBirthday() {
    return birthday;
  }

  /**
   * Standard getter for the Actor's ID
   *
   * @return the ID of the actor as an integer
   */
  public int getId() {
    return id;
  }

  /**
   * Standard getter for the IDs of the Movies the actor has been in
   *
   * @return the IDs of the movies the actor has been in
   */
  public List<Integer> getMovieIDs() {
    return movieIDs;
  }

  /**
   * Standard getter for the Actor's name
   *
   * @return the name of the actor as a String
   */
  public String getName() {
    return name;
  }
}
