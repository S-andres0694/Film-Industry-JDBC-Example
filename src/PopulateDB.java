import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PopulateDB {
  private static final File ACTORS_FILE = new File("Assignment2 JSON Files/Actors.json");
  private static final File AWARDS_FILE = new File("Assignment2 JSON Files/Awards.json");
  private static final File MOVIES_FILE = new File("Assignment2 JSON Files/Movies.json");
  private static final File DIRECTORS_FILE = new File("Assignment2 JSON Files/Directors.json");
  private static final File RATINGS_FILE = new File("Assignment2 JSON Files/Ratings.json");
  private static final File GENRES_FILE = new File("Assignment2 JSON Files/Genres.json");
  private static final Parsers parserObj = new Parsers();

  public static void main(String[] args) {
    PopulateDB obj = new PopulateDB();

    try {
      System.out.println(
          population(
              obj,
              ACTORS_FILE,
              AWARDS_FILE,
              MOVIES_FILE,
              DIRECTORS_FILE,
              RATINGS_FILE,
              GENRES_FILE));
    } catch (JSONSchemaException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Fills out the actors table from values obtained from the parser. Also fill out the
   * intermediate tables AwardWinningActor and Cast
   *
   * @param actorsFile the file which is going to be read, Since this calls the actorParser it
   *     handles if the file does not meet a valid schema
   * @throws ClassNotFoundException if the driver cannot be loaded
   * @throws SQLException if there is a problem introducing values because of bad SQL Code
   * @throws IOException if there is a problem reading the file. This is thrown from the
   *     actorsParser method
   * @throws JSONSchemaException if the passed file does not really meet
   */
  public void populateActors(File actorsFile)
      throws ClassNotFoundException, SQLException, IOException, JSONSchemaException {
    Connection neuralink = InitialiseDB.preparation();
    PreparedStatement statement =
        neuralink.prepareStatement("INSERT INTO Actors (name, age, birthday) VALUES (?, ?,?)");
    Actors[] values = parserObj.actorParser(actorsFile);

    for (Actors actor : values) {
      statement.setString(1, actor.getName());
      statement.setInt(2, actor.getAge());
      statement.setString(3, actor.getBirthday());
      statement.executeUpdate();
    }

    statement.close();
    neuralink.close();
    neuralink = InitialiseDB.preparation();
    statement =
        neuralink.prepareStatement(
            "INSERT INTO  awardWinningActor (actorID, awardID) VALUES  (?,?)");

    for (Actors actor : values) {
      if (!actor.getAwardIDs().isEmpty()) {
        if (actor.getAwardIDs().size() == 1) {
          statement.setInt(1, actor.getId());
          statement.setInt(2, actor.getAwardIDs().get(0));
          statement.executeUpdate();
        } else {
          for (int i = 0; i < actor.getAwardIDs().size(); i++) {
            statement.setInt(1, actor.getId());
            statement.setInt(2, actor.getAwardIDs().get(i));
            statement.executeUpdate();
          }
        }
      }
    }

    statement.close();
    neuralink.close();
    neuralink = InitialiseDB.preparation();
    statement = neuralink.prepareStatement("INSERT INTO  Cast (actorID, movieID) VALUES  (?,?)");

    for (Actors actor : values) {
      if (!actor.getMovieIDs().isEmpty()) {
        if (actor.getMovieIDs().size() == 1) {
          statement.setInt(1, actor.getId());
          statement.setInt(2, actor.getMovieIDs().get(0));
          statement.executeUpdate();
        } else {
          for (int i = 0; i < actor.getMovieIDs().size(); i++) {
            statement.setInt(1, actor.getId());
            statement.setInt(2, actor.getMovieIDs().get(i));
            statement.executeUpdate();
          }
        }
      }
    }

    neuralink.close();
    statement.close();

  }

  /**
   * Fills out the awards table based on values obtained from the parser
   *
   * @param awardsFile the file that is going to be read. Ideally it should be the corresponding
   *     custom JSON but handles it if otherwise
   * @throws ClassNotFoundException if the drivers are not loaded
   * @throws JSONSchemaException if the file does not meet the valid schema
   * @throws SQLException if the SQL Code has the wrong syntax
   * @throws IOException if the file cannot be read or parsed
   */
  public void populateAwards(File awardsFile)
      throws ClassNotFoundException, SQLException, IOException, JSONSchemaException {
    Connection neuralink = InitialiseDB.preparation();
    PreparedStatement statement =
        neuralink.prepareStatement("INSERT INTO Awards (awardInstitution, motive) VALUES (?, ?)");
    Awards[] values = parserObj.awardsParser(awardsFile);

    for (Awards awards : values) {
      statement.setString(1, awards.getAwardInstitution());
      statement.setString(2, awards.getMotive());
      statement.executeUpdate();
    }

    statement.close();
    neuralink.close();
  }

  /**
   * Fills out the directors table based on values obtained from the parser In this case also fills
   * out the intermediate table AwardWinningDirector
   *
   * @param directorsFile the file that is going to be read. Ideally it should be the corresponding
   *     custom JSON but handles it if otherwise
   * @throws ClassNotFoundException if the drivers are not loaded
   * @throws JSONSchemaException if the file does not meet the valid schema
   * @throws SQLException if the SQL Code has the wrong syntax
   * @throws IOException if the file cannot be read or parsed
   */
  public void populateDirectors(File directorsFile)
      throws SQLException, ClassNotFoundException, IOException, JSONSchemaException {
    Connection neuralink = InitialiseDB.preparation();
    PreparedStatement statement =
        neuralink.prepareStatement("INSERT INTO Director (name, movieCount) VALUES (?, ?)");
    Director[] values = parserObj.directorsParser(directorsFile);

    for (Director director : values) {
      statement.setString(1, director.getName());
      statement.setInt(2, director.getMovieCount());
      statement.executeUpdate();
    }

    neuralink.close();

    neuralink = InitialiseDB.preparation();
    statement =
        neuralink.prepareStatement(
            "INSERT INTO  awardWinningDirector (directorID, awardID) VALUES  (?,?)");

    for (Director director : values) {
      if (!director.getAwardIDs().isEmpty()) {
        if (director.getAwardIDs().size() == 1) {
          statement.setInt(1, director.getID());
          statement.setInt(2, director.getAwardIDs().get(0));
          statement.executeUpdate();
        } else {
          for (int i = 0; i < director.getAwardIDs().size(); i++) {
            statement.setInt(1, director.getID());
            statement.setInt(2, director.getAwardIDs().get(i));
            statement.executeUpdate();
          }
        }
      }
    }

    statement.close();
    neuralink.close();
  }

  /**
   * Fills out the genres table based on values obtained from the parser
   *
   * @param genreFile the file that is going to be read. Ideally it should be the corresponding
   *     custom JSON but handles it if otherwise
   * @throws ClassNotFoundException if the drivers are not loaded
   * @throws JSONSchemaException if the file does not meet the valid schema
   * @throws SQLException if the SQL Code has the wrong syntax
   * @throws IOException if the file cannot be read or parsed
   */
  public void populateGenres(File genreFile)
      throws ClassNotFoundException, JSONSchemaException, SQLException, IOException {
    Connection neuralink = InitialiseDB.preparation();
    PreparedStatement statement =
        neuralink.prepareStatement("INSERT INTO Genres (GenreName) VALUES (?)");
    Genre[] values = parserObj.genreParser(genreFile);

    for (Genre genres : values) {
      statement.setString(1, genres.getGenreName());
      statement.executeUpdate();
    }

    statement.close();
    neuralink.close();
  }

  /**
   * Fills out the movies table based on values obtained from the parser Also fills out the
   * intermediate tables AwardWinningMovie, MoviesWithRatings and MoviesWithGenres
   *
   * @param movieFile the file that is going to be read. Ideally it should be the corresponding
   *     custom JSON but handles it if otherwise
   * @throws ClassNotFoundException if the drivers are not loaded
   * @throws JSONSchemaException if the file does not meet the valid schema
   * @throws SQLException if the SQL Code has the wrong syntax
   * @throws IOException if the file cannot be read or parsed
   */
  public void populateMovies(File movieFile)
      throws ClassNotFoundException, SQLException, IOException, JSONSchemaException {
    Connection neuralink = InitialiseDB.preparation();
    PreparedStatement statement =
        neuralink.prepareStatement(
            "INSERT INTO Movies (name, releaseDate, plot, runningTime, directorID) VALUES (?, ?, ?, ?, ?)");
    Movies[] values = parserObj.moviesParser(movieFile);

    for (Movies movie : values) {
      statement.setString(1, movie.getTitle());
      statement.setString(2, movie.getReleaseDate());
      statement.setString(3, movie.getPlotSummary());
      statement.setString(4, movie.getRunningTime());
      statement.setInt(5, movie.getDirectorID());
      statement.executeUpdate();
    }

    statement.close();
    neuralink.close();
    neuralink = InitialiseDB.preparation();
    statement =
        neuralink.prepareStatement(
            "INSERT INTO  awardWinningMovie (movieID, awardID) VALUES  (?,?)");

    for (Movies movies : values) {
      if (!movies.getAwardIDs().isEmpty()) {
        if (movies.getAwardIDs().size() == 1 && movies.getAwardIDs().get(0) != 0) {
          statement.setInt(1, movies.getID());
          statement.setInt(2, movies.getAwardIDs().get(0));
          statement.executeUpdate();
        } else {
          for (int i = 0; i < movies.getAwardIDs().size(); i++) {
            statement.setInt(1, movies.getID());
            statement.setInt(2, movies.getAwardIDs().get(i));
            statement.executeUpdate();
          }
        }
      }
    }

    statement.close();
    neuralink.close();
    neuralink = InitialiseDB.preparation();
    statement =
        neuralink.prepareStatement(
            "INSERT INTO  MoviesWithRatings (movieID, criticID) VALUES  (?,?)");

    for (Movies movies : values) {
      if (!movies.getRatingsIDs().isEmpty()) {
        if (movies.getRatingsIDs().size() == 1) {
          statement.setInt(1, movies.getID());
          statement.setInt(2, movies.getRatingsIDs().get(0));
          statement.executeUpdate();
        } else {
          for (int i = 0; i < movies.getRatingsIDs().size(); i++) {
            statement.setInt(1, movies.getID());
            statement.setInt(2, movies.getRatingsIDs().get(i));
            statement.executeUpdate();
          }
        }
      }
    }

    statement.close();
    neuralink.close();
    neuralink = InitialiseDB.preparation();
    statement =
        neuralink.prepareStatement(
            "INSERT INTO  MoviesWithGenres (movieID, genreID) VALUES  (?,?)");

    for (Movies movies : values) {
      if (!movies.getGenreIDs().isEmpty()) {
        if (movies.getGenreIDs().size() == 1) {
          statement.setInt(1, movies.getID());
          statement.setInt(2, movies.getGenreIDs().get(0));
          statement.executeUpdate();
        } else {
          for (int i = 0; i < movies.getGenreIDs().size(); i++) {
            statement.setInt(1, movies.getID());
            statement.setInt(2, movies.getGenreIDs().get(i));
            statement.executeUpdate();
          }
        }
      }
    }
    neuralink.close();
    statement.close();
  }

  /**
   * Fills out the ratings table based on values obtained from the parser
   *
   * @param ratingsFile the file that is going to be read. Ideally it should be the corresponding
   *     custom JSON but handles it if otherwise
   * @throws ClassNotFoundException if the drivers are not loaded
   * @throws JSONSchemaException if the file does not meet the valid schema
   * @throws SQLException if the SQL Code has the wrong syntax
   * @throws IOException if the file cannot be read or parsed
   */
  public void populateRatings(File ratingsFile)
      throws ClassNotFoundException, SQLException, IOException, JSONSchemaException {
    Connection neuralink = InitialiseDB.preparation();
    PreparedStatement statement =
        neuralink.prepareStatement(
            "INSERT INTO Ratings (criticSource, ratingNumber) VALUES (?, ?)");
    Ratings[] values = parserObj.ratingsParser(ratingsFile);

    for (Ratings rating : values) {
      statement.setString(1, rating.getCriticSource());
      statement.setDouble(2, rating.getRating());
      statement.executeUpdate();
    }
    neuralink.close();
    statement.close();

  }

  /**
   * Fills out all the tables by calling the different populate methods in a bulk. This is done
   * for better testing and commodity
   *
   * @param obj the object what allows calling the different populate methods
   * @param actorFile file that contains the values for the actors table and its intermediate tables
   * @param awardFile the file that contains the values for the awards table
   * @param movieFile file that contains the values for the movies table and its intermediate tables
   * @param directorFile file that contains the values for the director table and its intermediate
   *     table
   * @param ratingsFile file that contains the values for the ratings table
   * @param genreFile file that contains the values for the genre table
   * @return string that communicates the output of the operation
   * @throws JSONSchemaException if any of the files do not meet the schema
   */
  public static String population(
      PopulateDB obj,
      File actorFile,
      File awardFile,
      File movieFile,
      File directorFile,
      File ratingsFile,
      File genreFile)
      throws JSONSchemaException {
    File db = new File(InitialiseDB.getDatabaseFile());

    if (!(InitialiseDB.emptyDBChecker()) && db.exists()) {
      try {
        if (!QueryDB.populatedTableChecker()) {
          obj.populateAwards(awardFile);
          obj.populateDirectors(directorFile);
          obj.populateGenres(genreFile);
          obj.populateRatings(ratingsFile);
          obj.populateMovies(movieFile);
          obj.populateActors(actorFile);
        } else {
          return "The table was already populated. Nothing happened";
        }
      } catch (ClassNotFoundException e) {
        return ("There is a problem with the drivers. Please check the SQLite Drivers");
      } catch (SQLException e) {
        return ("There is an error with the SQL Code of the Population. Please check the SQL Code");
      } catch (IOException e) {
        return ("There has been a problem parsing through the JSON Files. Please check the JSON Files location or integrity");
      }
      return ("The tables have been populated");
    } else {
      return ("The tables have not been created. Thus, it cannot be populated");
    }
  }

  /**
   * Standard getter for the custom Actors JSON
   *
   * @return the Custom Actors JSON
   */
  public static File getActorsFile() {
    return ACTORS_FILE;
  }

  /**
   * Standard getter for the custom Awards JSON
   *
   * @return the custom Awards JSON
   */
  public static File getAwardsFile() {
    return AWARDS_FILE;
  }

  /**
   * Standard getter for the custom Directors JSON
   *
   * @return the custom Directors JSON
   */
  public static File getDirectorsFile() {
    return DIRECTORS_FILE;
  }

  /**
   * Standard getter for the Genres Custom JSON
   *
   * @return The Genres Custom JSON
   */
  public static File getGenresFile() {
    return GENRES_FILE;
  }

  /**
   * Standard getter for the custom Movies JSON
   *
   * @return the custom Movies JSON
   */
  public static File getMoviesFile() {
    return MOVIES_FILE;
  }

  /**
   * @return the custom Ratings JSON
   */
  public static File getRatingsFile() {
    return RATINGS_FILE;
  }
}
