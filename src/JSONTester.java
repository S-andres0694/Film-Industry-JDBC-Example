import java.io.*;

import java.util.stream.Collectors;

/**
 * Used to test the integrity of files used in the parsing process. To do this, it compares them
 * with the referenced files in the Assignment2 JSON Files folder
 *
 * @author 230018374
 */
public class JSONTester {

  /**
   * Compares a file with the Actors JSON to see if they are the same
   *
   * @return true if this is the case, false if it is not
   */
  public static boolean JSONActorsComparer(File comparedFile)
      throws IOException, JSONSchemaException {
    BufferedReader reader1 = new BufferedReader(new FileReader(PopulateDB.getActorsFile()));
    BufferedReader reader2 = new BufferedReader(new FileReader(comparedFile));

    if (reader1
        .lines()
        .toList()
        .equals(reader2.lines().collect(Collectors.toList()))) {
      reader1.close();
      reader2.close();
      return true;
    } else {
      throw new JSONSchemaException(
          "The file " + comparedFile.getName() + "does not meet the schema requirements");
    }
  }

  /**
   * Compares a file with the Awards JSON to see if they are the same
   *
   * @return true if this is the case, false if it is not
   */
  public static boolean JSONAwardsComparer(File comparedFile)
      throws IOException, JSONSchemaException {
    BufferedReader reader1 = new BufferedReader(new FileReader(PopulateDB.getAwardsFile()));
    BufferedReader reader2 = new BufferedReader(new FileReader(comparedFile));

    if (reader1
        .lines()
        .toList()
        .equals(reader2.lines().collect(Collectors.toList()))) {
      reader1.close();
      reader2.close();
      return true;
    } else {
      throw new JSONSchemaException(
          "The file " + comparedFile.getName() + "does not meet the schema requirements");
    }
  }

  /**
   * Compares a file with the Directors JSON to see if they are the same
   *
   * @return true if this is the case, false if it is not
   */
  public static boolean JSONDirectorsComparer(File comparedFile)
      throws IOException, JSONSchemaException {
    BufferedReader reader1 = new BufferedReader(new FileReader(PopulateDB.getDirectorsFile()));
    BufferedReader reader2 = new BufferedReader(new FileReader(comparedFile));

    if (reader1
        .lines()
        .toList()
        .equals(reader2.lines().collect(Collectors.toList()))) {
      reader1.close();
      reader2.close();
      return true;
    } else {
      throw new JSONSchemaException(
          "The file " + comparedFile.getName() + "does not meet the schema requirements");
    }
  }

  /**
   * Compares a file with the Genres JSON to see if they are the same
   *
   * @return true if this is the case, false if it is not
   */
  public static boolean JSONGenresComparer(File comparedFile)
      throws IOException, JSONSchemaException {
    BufferedReader reader1 = new BufferedReader(new FileReader(PopulateDB.getGenresFile()));
    BufferedReader reader2 = new BufferedReader(new FileReader(comparedFile));

    if (reader1
        .lines()
        .toList()
        .equals(reader2.lines().collect(Collectors.toList()))) {
      reader1.close();
      reader2.close();
      return true;
    } else {
      throw new JSONSchemaException(
          "The file " + comparedFile.getName() + "does not meet the schema requirements");
    }
  }

  /**
   * Compares a file with the Movies JSON to see if they are the same
   *
   * @return true if this is the case, false if it is not
   */
  public static boolean JSONMoviesComparer(File comparedFile)
      throws IOException, JSONSchemaException {
    BufferedReader reader1 = new BufferedReader(new FileReader(PopulateDB.getMoviesFile()));
    BufferedReader reader2 = new BufferedReader(new FileReader(comparedFile));

    if (reader1
        .lines()
        .toList()
        .equals(reader2.lines().collect(Collectors.toList()))) {
      reader1.close();
      reader2.close();
      return true;
    } else {
      throw new JSONSchemaException(
          "The file " + comparedFile.getName() + "does not meet the schema requirements");
    }
  }

  /**
   * Compares a file with the Ratings JSON to see if they are the same
   *
   * @return true if this is the case, false if it is not
   */
  public static boolean JSONRatingsComparer(File comparedFile)
      throws IOException, JSONSchemaException {
    BufferedReader reader1 = new BufferedReader(new FileReader(PopulateDB.getRatingsFile()));
    BufferedReader reader2 = new BufferedReader(new FileReader(comparedFile));

    if (reader1
        .lines()
        .toList()
        .equals(reader2.lines().collect(Collectors.toList()))) {
      reader1.close();
      reader2.close();
      return true;
    } else {
      throw new JSONSchemaException(
          "The file " + comparedFile.getName() + "does not meet the schema requirements");
    }
  }
}
