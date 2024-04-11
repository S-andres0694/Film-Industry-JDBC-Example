import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.*;

/**
 * DatabaseTests contains the JUnit Tests that are testing multiple failure and exception cases that
 * could occur during the operation of the code, as well as just the desired outputs of the code.
 * The testing of methods who print to the console as their final output was possible because of the
 * tutorial sourced by: <a href="https://www.baeldung.com/java-testing-system-out-println">...</a>
 *
 * @author 230018374
 */

public class DatabaseTests {
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private PopulateDB populateDB;
  private File file;

  /**
   * Sets up some conditions for the testing. It deletes any database file to make sure that it is
   * running from nothing, It initialises a populateDB object for the table population, it
   * initialises a random file, which might or might not be actually created to be tested against
   * and also uses the last piece of code for the testing of methods who print out something
   */
  @Before
  public void setup() {
    populateDB = new PopulateDB();
    file = new File("Random.txt");
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  /**
   * Cleanup after the test. It deletes any possible database file that might have remained, and it
   * deletes any file that could have been created, it clears out the printing
   */
  @After
  public void cleanup() {
    populateDB = null;
    file.delete();
    System.setOut(standardOut);
  }

  /**
   * Tests for the normal creation of the database file
   */
  @Test
  public void test1() {
    new File(InitialiseDB.getDatabaseFile()).delete();
    Assert.assertEquals("OK", InitialiseDB.initialiseDB(InitialiseDB.getSqlScript()));
    Assert.assertTrue(new File(InitialiseDB.getDatabaseFile()).exists());
  }

  /**
   * Tests what happens if you pass a non-existent file as the DDL Script
   */
  @Test
  public void test2() {
    Assert.assertEquals(
            "The DDL Script cannot be read or it cannot be detected. Please check the DDL Script",
            InitialiseDB.initialiseDB("non-existent file.txt"));
  }

  /**
   * Supplies a random SQL file to test against the DDLTester
   */
  @Test
  public void test3() {

    File randomFile = new File("Random File.sql");

    try {
      randomFile.createNewFile();
    } catch (IOException e) {
      Assert.fail();
    }

    Assert.assertEquals(
            "The DDL Script schema has not been validated. Therefore, the table was not created",
            InitialiseDB.initialiseDB(randomFile.getPath()));
    randomFile.delete();
  }

  /**
   * Tests the JSON Tester, as it should detect if the correct exception is being thrown (which
   * indicates the parser works). This is done for the Actors JSON
   *
   * @throws JSONSchemaException if this exception is thrown, the test passes
   */
  @Test(expected = JSONSchemaException.class)
  public void test4() throws JSONSchemaException {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      file.createNewFile();
    } catch (IOException e) {
      Assert.fail();
    }

    System.out.println( PopulateDB.population(
            populateDB,
            file,
            PopulateDB.getAwardsFile(),
            PopulateDB.getMoviesFile(),
            PopulateDB.getDirectorsFile(),
            PopulateDB.getRatingsFile(),
            PopulateDB.getGenresFile()));
    file.delete();
  }

  /**
   * Tests the JSON Tester, as it should detect if the correct exception is being thrown (which
   * indicates the parser works) This is done for the Awards JSON
   *
   * @throws JSONSchemaException if this exception is thrown, the test passes
   */
  @Test(expected = JSONSchemaException.class)
  public void test7() throws JSONSchemaException {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      file.createNewFile();
    } catch (IOException e) {
      Assert.fail();
    }

    PopulateDB.population(
            populateDB,
            PopulateDB.getActorsFile(),
            file,
            PopulateDB.getMoviesFile(),
            PopulateDB.getDirectorsFile(),
            PopulateDB.getRatingsFile(),
            PopulateDB.getGenresFile());
    file.delete();
  }

  /**
   * Tests the JSON Tester, as it should detect if the correct exception is being thrown (which
   * indicates the parser works) This is done for the Movies JSON
   *
   * @throws JSONSchemaException ifn this exception is thrown, the test passes
   */

  @Test(expected = JSONSchemaException.class)
  public void test8() throws JSONSchemaException {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      file.createNewFile();
    } catch (IOException e) {
      Assert.fail();
    }

    PopulateDB.population(
            populateDB,
            PopulateDB.getActorsFile(),
            PopulateDB.getAwardsFile(),
            file,
            PopulateDB.getDirectorsFile(),
            PopulateDB.getRatingsFile(),
            PopulateDB.getGenresFile());
    file.delete();
  }

  /**
   * Makes sure that the JSON Tester works by creating a new random text file and passing it through
   * the tester This specific test is for the Director JSON
   */
  @Test(expected = JSONSchemaException.class)
  public void test9() throws JSONSchemaException {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      file.createNewFile();
    } catch (IOException e) {
      Assert.fail();
    }

    PopulateDB.population(
            populateDB,
            PopulateDB.getActorsFile(),
            PopulateDB.getAwardsFile(),
            PopulateDB.getMoviesFile(),
            file,
            PopulateDB.getRatingsFile(),
            PopulateDB.getGenresFile());
    file.delete();
  }

  /**
   * Makes sure that the JSON Tester works by creating a new random text file and passing it through
   * the tester This specific test is for the Ratings JSON
   */
  @Test(expected = JSONSchemaException.class)
  public void test6() throws JSONSchemaException {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      file.createNewFile();
    } catch (IOException e) {
      Assert.fail();
    }

    PopulateDB.population(
            populateDB,
            PopulateDB.getActorsFile(),
            PopulateDB.getAwardsFile(),
            PopulateDB.getMoviesFile(),
            PopulateDB.getDirectorsFile(),
            file,
            PopulateDB.getGenresFile());
    file.delete();
  }

  /**
   * Makes sure that the JSON Tester works by creating a new random text file and passing it through
   * the tester This specific test is for the Genres JSON
   */
  @Test(expected = JSONSchemaException.class)
  public void test10() throws JSONSchemaException {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      file.createNewFile();
    } catch (IOException e) {
      Assert.fail();
    }

    PopulateDB.population(
            populateDB,
            PopulateDB.getActorsFile(),
            PopulateDB.getAwardsFile(),
            PopulateDB.getMoviesFile(),
            PopulateDB.getDirectorsFile(),
            PopulateDB.getRatingsFile(),
            file);
    file.delete();
  }

  /**
   * Makes sure that it prints out the correct message if the query returns no values
   */
  @Test
  public void test11() {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try (Connection conn = InitialiseDB.preparation()) {

      Statement stmnt = conn.createStatement();

      QueryDB.printer(stmnt.executeQuery("SELECT a.name FROM Actors a WHERE a.age = 1000"));
      Assert.assertEquals(
              "Based on the inputted data, there are no matches. Please try again",
              outputStreamCaptor.toString().trim());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Analyzes what would happen if you passed numbers in string parameters in the second query. Also
   * tests for the query with no retrieval at the same time
   */
  @Test
  public void test12() {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.printer(QueryDB.executeQuery2("2354"));
      Assert.assertEquals(
              "Based on the inputted data, there are no matches. Please try again",
              outputStreamCaptor.toString().trim());
    } catch (ClassNotFoundException e) {
      Assert.fail();
    } catch (SQLException e) {
      Assert.fail();
    }
  }

  /**
   * Analyzes what would happen if you passed wrong values in string parameters in the third query.
   * Also tests for a query with no retrieval at the same time
   */
  @Test
  public void test13() {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.printer(QueryDB.executeQuery3("2354", "McPhall DiLorenzo"));
      Assert.assertEquals(
              "Based on the inputted data, there are no matches. Please try again",
              outputStreamCaptor.toString().trim());
    } catch (ClassNotFoundException e) {
      Assert.fail();
    } catch (SQLException e) {
      Assert.fail();
    }
  }

  /**
   * Tests out if the fourth query prints out the correct message if there are no matches
   */
  @Test
  public void test14() {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.printer(QueryDB.executeQuery4("ivbaspADSIVUBEWPID"));
      Assert.assertEquals(
              "Based on the inputted data, there are no matches. Please try again",
              outputStreamCaptor.toString().trim());
    } catch (ClassNotFoundException e) {
      Assert.fail();
    } catch (SQLException e) {
      Assert.fail();
    }
  }

  /**
   * Tests out if the fifth query prints out the correct message if there are no matches
   */
  @Test
  public void test15() {
    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.printer(QueryDB.executeQuery5("jasjasdajn"));
      Assert.assertEquals(
              "Based on the inputted data, there are no matches. Please try again",
              outputStreamCaptor.toString().trim());
    } catch (ClassNotFoundException e) {
      Assert.fail();
    } catch (SQLException e) {
      Assert.fail();
    }
  }

  /**
   * Evaluates what happens if you pass null values into the querying method on query 5
   */
  @Test
  public void test16() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[2];
    args[0] = String.valueOf(5);

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
    Assert.assertEquals(
            "Based on the inputted data, there are no matches. Please try again", outputStreamCaptor.toString().trim());
  }

  /**
   * Passes an array of a random length in querying method when args[0] = 2
   */
  @Test
  public void test17() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[10];
    args[0] = String.valueOf(2);

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
    Assert.assertEquals("Incorrect number of arguments", outputStreamCaptor.toString().trim());
  }

  /**
   * Passes an array of a random length in querying method when args[0] = 3
   */
  @Test
  public void test18() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[10];
    args[0] = String.valueOf(3);

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
    Assert.assertEquals("Incorrect number of arguments", outputStreamCaptor.toString().trim());
  }

  /**
   * Passes an array of a random length in querying method when args[0] = 4
   */
  @Test
  public void test19() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[10];
    args[0] = String.valueOf(4);

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
    Assert.assertEquals("Incorrect number of arguments", outputStreamCaptor.toString().trim());
  }

  /**
   * Passes an array of a random length in querying method when args[0] = 5
   */
  @Test
  public void test20() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[10];
    args[0] = String.valueOf(5);

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
    Assert.assertEquals("Incorrect number of arguments", outputStreamCaptor.toString().trim());
  }

  /**
   * Passes an array of a random length in querying method when args[0] = 6
   */
  @Test
  public void test21() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[10];
    args[0] = String.valueOf(6);

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
    Assert.assertEquals("Incorrect number of arguments", outputStreamCaptor.toString().trim());
  }

  /**
   * Passes an array of a random length in querying method when args[0] = 1
   */
  @Test
  public void test22() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[10];
    args[0] = String.valueOf(1);

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
    Assert.assertEquals("Incorrect number of arguments", outputStreamCaptor.toString().trim());
  }


  /**
   * Tests null values in a query that requires a string
   */
  @Test
  public void test24() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[2];
    args[0] = String.valueOf(2);
    args[1] = null;

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
    Assert.assertEquals(
            "Based on the inputted data, there are no matches. Please try again",
            outputStreamCaptor.toString().trim());
  }

  /**
   * Makes sure that it throws the correct exception if the first parameter of the args array is a
   * string integer value
   */
  @Test(expected = NumberFormatException.class)
  public void test25() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[2];
    args[0] = "vb[irw[bvirw[bvirw";

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
  }

  /**
   * Makes sure that it throws the correct exception if the first parameter of the args array is a
   * double value
   */
  @Test(expected = NumberFormatException.class)
  public void test26() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[2];
    args[0] = "1.87";

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }
  }

  /**
   * Makes sure that the database is actually being cleared using the DBDelete method
   */

  @Test
  public void test27() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    try {
      InitialiseDB.deleteDB(DDLTester.deletionTester(), new File(InitialiseDB.getSqlDeletionScript()));
      Assert.assertTrue(InitialiseDB.emptyDBChecker());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      Assert.fail();
    } catch (IOException e) {
      Assert.fail();
    }

  }

  /**
   * Avoids querying when the table has not been populated
   */

  @Test
  public void test28() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());
    String[] args = new String[2];
    args[0] = "2";
    args[1] = "The Matrix(1999)";
    try {
      QueryDB.querying(args);
    } catch (SQLException e) {
      Assert.fail();
    } catch (ClassNotFoundException e) {
      Assert.fail();
    }

    Assert.assertEquals("The table has not been populated. Please try again", outputStreamCaptor.toString().trim());

  }

  /**
   * Makes sure that the table does not get populated more than once
   */

  @Test
  public void test29() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());
      System.out.println(PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile()));
    } catch (JSONSchemaException e) {
      Assert.fail();
    }

    Assert.assertEquals("The table was already populated. Nothing happened", outputStreamCaptor.toString().trim());
  }

  /**
   * Tests out for the appropriate table population
   */

  @Test
  public void test30() {

    InitialiseDB.initialiseDB(InitialiseDB.getSqlScript());

    try {
      PopulateDB.population(
              populateDB,
              PopulateDB.getActorsFile(),
              PopulateDB.getAwardsFile(),
              PopulateDB.getMoviesFile(),
              PopulateDB.getDirectorsFile(),
              PopulateDB.getRatingsFile(),
              PopulateDB.getGenresFile());

    } catch (JSONSchemaException e) {
      Assert.fail();
    }
    Assert.assertFalse(InitialiseDB.emptyDBChecker());

  }


}
