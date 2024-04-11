import java.sql.*;

/**
 * Allows for the retrieval of data from the database. This is done through a series of queries
 * which read data from the terminal parameters (only done if needed). It also uses terminal
 * parameters to determine which query is going to be retrieved
 *
 * @author 230018374
 */
public class QueryDB {

    /**
     * Runs a query which contains all the names of all the movies in the database
     *
     * @return a result set that contains all the names of all the movies in the database
     * @throws ClassNotFoundException if the drivers are not loaded
     * @throws SQLException           if there is a problem with the SQL Code
     */
    public static ResultSet executeQuery1() throws ClassNotFoundException, SQLException {
        Statement statement;

        Connection connection = InitialiseDB.preparation();
        statement = connection.createStatement();
        return statement.executeQuery("SELECT m.name as 'Name' FROM Movies m");

    }

    /**
     * Runs a query which contains the names of the actors who perform in some specific movie inputted
     * through the command line arguments
     *
     * @return a result set that contains the names of the actors who perform in some specific movie
     * inputted through the command line arguments
     * @throws ClassNotFoundException if the drivers are not loaded
     * @throws SQLException           if there is a problem with the SQL Code
     */
    public static ResultSet executeQuery2(String movie) throws ClassNotFoundException, SQLException {
        PreparedStatement statement;
        Connection connection = InitialiseDB.preparation();
        statement =
                connection.prepareStatement(
                        "SELECT a.name as 'Actor Name'FROM Movies m INNER JOIN Cast c ON m.movieID = c.movieID INNER JOIN Actors a ON a.actorID = c.actorID WHERE m.name = ?");
        statement.setString(1, movie);
        return statement.executeQuery();


    }

    /**
     * Runs a query which contains the synopses of a movie with a specified actor in it and directed
     * by some particular director.
     *
     * @return a result set that contains the synopses of a movie with a specified actor in it and
     * directed by some particular director. The actor and director are inputted using the
     * command line arguments.
     * @throws ClassNotFoundException if the drivers are not loaded
     * @throws SQLException           if there is a problem with the SQL Code
     */
    public static ResultSet executeQuery3(String actor, String director)
            throws SQLException, ClassNotFoundException {
        PreparedStatement statement;
        Connection connection = InitialiseDB.preparation();
        statement =
                connection.prepareStatement(
                        "SELECT m.plot as 'Plot' FROM Movies m INNER JOIN Cast c ON m.movieID = c.movieID INNER JOIN Actors a ON a.actorID = c.actorID INNER JOIN Director D on D.directorID = m.directorID WHERE a.name = ? AND D.name = ?");
        statement.setString(1, actor);
        statement.setString(2, director);
        return statement.executeQuery();


    }

    /**
     * Runs a query which contains the directors of the movies that have a particular actor in them.
     *
     * @return a result set that contains the directors of the movies that have a particular actor in
     * them. The actor is inputted using the command line arguments.
     * @throws ClassNotFoundException if the drivers are not loaded
     * @throws SQLException           if there is a problem with the SQL Code
     */
    public static ResultSet executeQuery4(String actor) throws SQLException, ClassNotFoundException {
        PreparedStatement statement;
        Connection connection = InitialiseDB.preparation();
        statement =
                connection.prepareStatement(
                        "SELECT d.name as 'Director', A.name as 'Particular Actor' FROM Director d INNER JOIN main.Movies M on d.directorID = M.directorID INNER JOIN main.Cast C on M.movieID = C.movieID INNER JOIN main.Actors A on A.actorID = C.actorID WHERE A.name = ?");
        statement.setString(1, actor);
        return statement.executeQuery();


    }

    /**
     * Runs a query which contains the movies directed by a director with more than three movies directed,
     * who also have a critic from Rotten Tomatoes better than 4 and have been released in a
     * particular year, while also matching with a particular genre. This is returned in alphabetical
     * order
     *
     * @return a result set that contains the movies directed by a director with more than three movies
     * directed, who also have a critic from Rotten Tomatoes better than 4
     * while also matching with a particular genre.
     * @throws ClassNotFoundException if the drivers are not loaded
     * @throws SQLException           if there is a problem with the SQL Code
     */
    public static ResultSet executeQuery5(String genre)
            throws SQLException, ClassNotFoundException {
        PreparedStatement statement;
        Connection connection = InitialiseDB.preparation();
        statement =
                connection.prepareStatement(
                        "SELECT m.name FROM Movies m INNER JOIN Director D on D.directorID = m.directorID INNER JOIN MoviesWithRatings MWR on m.movieID = MWR.movieID INNER JOIN main.Ratings R on R.criticID = MWR.criticID INNER JOIN main.MoviesWithGenres MWG on m.movieID = MWG.movieID INNER JOIN main.Genres G on G.genreID = MWG.genreID WHERE R.criticSource = ? AND G.GenreName = ?");
        statement.setString(1, "Rotten Tomatoes");
        statement.setString(2, genre);
        return statement.executeQuery();
    }

    public static void main(String[] args) {
        try {
            querying(args);
        } catch (NumberFormatException error) {
            System.out.println(error.getMessage());
        } catch (SQLException error) {
            System.out.println("There has been a problem with the database during population validation. Please try again");
        } catch (ClassNotFoundException error) {
            System.out.println("The drivers have not been initialised");
        }
    }

    /**
     * Prints the results of the queries in a human-readable way. It first indicates the entry number
     * (in order of printing, it is not the actual entry number of the table) and then the column name
     * with its respective value
     *
     * @param set the result set returned by the appropriate query
     * @throws SQLException if there is an SQL Code error
     */
    public static void printer(ResultSet set) throws SQLException {
        int count = 1;

        if (resultSetChecker(set)) {
            while (set.next()) {
                System.out.println("Entry " + count);

                for (int i = 1; i < set.getMetaData().getColumnCount() + 1; i++) {
                    System.out.println(set.getMetaData().getColumnName(i) + ": " + set.getString(i));
                }

                count++;
            }
        } else {
            System.out.println("Based on the inputted data, there are no matches. Please try again");
        }
    }

    /**
     * Based on the values of elements inside the array, it runs certain queries. Using those
     * elements, it also fills out the values for queries that require input such as queries 2,3,4 and
     * 5
     *
     * @param array can handle any array, but ideally it is going to be used for the Args[] array of
     *              the main method
     */
    public static void querying(String[] array) throws SQLException, ClassNotFoundException {
        int parameter = 0;

        if (!InitialiseDB.emptyDBChecker() && populatedTableChecker()) {
            try {
                if (array.length < 1) {
                    System.out.println("There is no args parameter. Please try again");
                } else {
                    parameter = Integer.parseInt(array[0]);
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException(
                        "The first parameter of the args array is not a number. Please try again");

            }

            switch (parameter) {
                case 1:
                    try {
                        if (array.length == 1) {
                            printer(executeQuery1());
                        } else {
                            System.out.println("Incorrect number of arguments");
                        }
                    } catch (SQLException e) {
                        System.out.println(
                                "SQL Code Error has been detected. Please try again with fixed code");
                    } catch (ClassNotFoundException e) {
                        System.out.println("There has been a problem loading the drivers. Please try again");
                    }

                    break;

                case 2:
                    try {
                        if (array.length == 2) {
                            printer(executeQuery2(array[1]));
                        } else {
                            System.out.println("Incorrect number of arguments");
                        }
                    } catch (SQLException e) {
                        System.out.println(
                                "SQL Code Error has been detected. Please try again with fixed code");
                    } catch (ClassNotFoundException e) {
                        System.out.println("There has been a problem loading the drivers. Please try again");
                    }

                    break;

                case 3:
                    try {
                        if (array.length == 3) {
                            printer(executeQuery3(array[1], array[2]));
                        } else {
                            System.out.println("Incorrect number of arguments");
                        }
                    } catch (SQLException e) {
                        System.out.println(
                                "SQL Code Error has been detected. Please try again with fixed code");
                    } catch (ClassNotFoundException e) {
                        System.out.println("There has been a problem loading the drivers. Please try again");
                    }

                    break;

                case 4:
                    try {
                        if (array.length == 2) {
                            printer(executeQuery4(array[1]));
                        } else {
                            System.out.println("Incorrect number of arguments");
                        }
                    } catch (SQLException e) {
                        System.out.println(
                                "SQL Code Error has been detected. Please try again with fixed code");
                    } catch (ClassNotFoundException e) {
                        System.out.println("There has been a problem loading the drivers. Please try again");
                    }

                    break;

                case 5:
                    try {
                        if (array.length == 2) {
                            printer(executeQuery5(array[1]));
                        } else {
                            System.out.println("Incorrect number of arguments");
                        }
                    } catch (SQLException e) {
                        System.out.println(
                                "SQL Code Error has been detected. Please try again with fixed code");
                    } catch (ClassNotFoundException e) {
                        System.out.println("There has been a problem loading the drivers. Please try again");
                    } catch (NumberFormatException error) {
                        System.out.println("Argument 1 is not an integer. Please try again");
                    }

                    break;

                case 6:
                    try {
                        if (array.length == 1) {
                            printer(executeQuery6());
                        } else {
                            System.out.println("Incorrect number of arguments");
                        }
                    } catch (SQLException e) {
                        System.out.println(
                                "SQL Code Error has been detected. Please try again with fixed code");
                    } catch (ClassNotFoundException e) {
                        System.out.println("There has been a problem loading the drivers. Please try again");
                    }
                    break;

                default:
                    System.out.println("The value is not recognised. Please try with a number between 1-6");
            }
        } else {
            System.out.println("The table has not been populated. Please try again");
        }
    }

    /**
     * Makes sure that the retrieved result set is not empty. This code was sourced from: //<a
     * href="https://stackoverflow.com/questions/867194/java-resultset-how-to-check-if-there-are-any-results">...</a>
     *
     * @param rs the result set given by any of the queries
     * @return true if it's not empty and false if it is
     * @throws SQLException if there is a problem with the SQL Code
     */
    public static boolean resultSetChecker(ResultSet rs) throws SQLException {
        return (rs.isBeforeFirst() && (rs.getRow() == 0));
    }

    /**
     * <p>
     * Retrieves the average age of winners of all of the awards that can be won by an actor
     * </p>
     *
     * @return a result set object containing average age of winners of all of the awards that can be won by an actor
     * @throws SQLException           if there are any issues with the database
     * @throws ClassNotFoundException if the drivers have not been loaded
     */

    public static ResultSet executeQuery6() throws SQLException,
            ClassNotFoundException {
        Connection connection = InitialiseDB.preparation();
        PreparedStatement statement =
                connection.prepareStatement("SELECT ROUND(AVG(a.age)) as 'Average Age', A2.awardInstitution as 'Award Institution', A2.motive as 'Motive' " +
                        "FROM Actors a " +
                        "INNER JOIN main.awardWinningActor aWA on a.actorID = aWA.actorID " +
                        "INNER JOIN main.Awards A2 on A2.awardID = aWA.awardID GROUP BY awardInstitution");
        return statement.executeQuery();
    }
    public static boolean populatedTableChecker() throws SQLException, ClassNotFoundException {

        try (Connection conn = InitialiseDB.preparation()){
            return resultSetChecker(conn.createStatement().executeQuery("SELECT * FROM Actors"));
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException();
        } catch (SQLException e) {
            throw new SQLException();
        }


    }
}
