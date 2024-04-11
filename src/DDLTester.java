import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of the same statements inside the DDLScript. This is used to compare files
 * with the correct SQL Script to ensure proper database initialisation.
 *
 * @author 230018374
 */
public class DDLTester {

  /**
   * <p>
   * Possesses all the valid SQL Statements that build the table that form my database. This
   * method is used to compare with other files to determine if they are a valid SQL Script for this
   * database.
   *</p>
   * @return the valid order and content that the SQLScript that is going to be run should have in
   *     the form of a list of strings
   */
  public static List<String> tester() {
    List<String> validSQL = new ArrayList<>();

    validSQL.add(
        "CREATE TABLE IF NOT EXISTS Actors(    actorID INTEGER PRIMARY KEY AUTOINCREMENT,    name VARCHAR(80) NOT NULL,    age INTEGER NOT NULL,    birthday DATE NOT NULL)");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS Director(    directorID INTEGER PRIMARY KEY AUTOINCREMENT,    name VARCHAR (100) NOT NULL,    movieCount INTEGER CHECK (movieCount >=1 ))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS Movies(    movieID INTEGER PRIMARY KEY AUTOINCREMENT,    name VARCHAR(200) NOT NULL,    releaseDate DATE NOT NULL,    plot VARCHAR(500) NOT NULL,    runningTime VARCHAR(8) NOT NULL,    directorID INTEGER NOT NULL,    FOREIGN KEY (directorID) REFERENCES Director(directorID))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS Awards(    awardID INTEGER PRIMARY KEY AUTOINCREMENT,    awardInstitution VARCHAR(20),    motive VARCHAR(100))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS Cast(    actorID INTEGER NOT NULL,    movieID INTEGER NOT NULL,    FOREIGN KEY (actorID) REFERENCES  Actors(actorID),    FOREIGN KEY (movieID) REFERENCES  Movies(movieID))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS awardWinningActor(    actorID INTEGER NOT NULL,    awardID INTEGER NOT NULL,    FOREIGN KEY (actorID) REFERENCES Actors(actorID),    FOREIGN KEY (awardID) REFERENCES Awards(awardID))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS awardWinningMovie(    movieID INTEGER NOT NULL,    awardID INTEGER NOT NULL,    FOREIGN KEY (movieID) REFERENCES Movies(movieID),    FOREIGN KEY (awardID) REFERENCES Awards(awardID))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS awardWinningDirector(    directorID NOT NULL,    awardID INTEGER NOT NULL,    FOREIGN KEY (directorID) REFERENCES Director(directorID),    FOREIGN KEY (awardID) REFERENCES Awards(awardID))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS Ratings(    criticID INTEGER PRIMARY KEY AUTOINCREMENT,    criticSource VARCHAR (100),    ratingNumber FLOAT CHECK (ratingNumber < 5.0))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS MoviesWithRatings(        movieID INTEGER NOT NULL,        criticID INTEGER,        FOREIGN KEY (movieID) REFERENCES Movies(movieID),        FOREIGN KEY (criticID) REFERENCES Ratings(criticID))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS Genres(    genreID INTEGER PRIMARY KEY AUTOINCREMENT,    GenreName VARCHAR (100))");
    validSQL.add(
        "CREATE TABLE IF NOT EXISTS MoviesWithGenres(    movieID INTEGER NOT NULL,    genreID INTEGER NOT NULL,    FOREIGN KEY (movieID) REFERENCES Movies(movieID),    FOREIGN KEY (genreID) REFERENCES Genres(genreID))");

    return validSQL;
  }

  /**
   * <p>
   *     Contains a script that deletes all of the tables of the database without deleting
   *     the database file as per specification.
   * </p>
   *
   *
   * @return a list of strings containing the correct commands for the SQL Script that
   * clears the database
   */

  public static List<String> deletionTester() {

    List<String> validSQL = new ArrayList<>();
    validSQL.add("DROP TABLE IF EXISTS awardWinningActor");
    validSQL.add("DROP TABLE IF EXISTS awardWinningDirector");
    validSQL.add("DROP TABLE IF EXISTS awardWinningMovie");
    validSQL.add("DROP TABLE IF EXISTS Cast");
    validSQL.add("DROP TABLE IF EXISTS MoviesWithGenres");
    validSQL.add("DROP TABLE IF EXISTS MoviesWithRatings");
    validSQL.add("DROP TABLE IF EXISTS Actors");
    validSQL.add("DROP TABLE IF EXISTS Awards");
    validSQL.add("DROP TABLE IF EXISTS Movies");
    validSQL.add("DROP TABLE IF EXISTS Director");
    validSQL.add("DROP TABLE IF EXISTS Genres");
    validSQL.add("DROP TABLE IF EXISTS Ratings");

    return validSQL;

  }
}
