import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used to create the database file and give it its correct constraints, as specified by the
 * DDLScript. It also deletes the database file if it is already present during runtime
 *
 * @author 230018374
 */
public class InitialiseDB {
    private static final String DATABASE_FILE = "Movies&Actors";
    private static final String SQL_SCRIPT = "DDLScript.sql";

    private static final String SQL_DELETION_SCRIPT = "TableClearDDL.sql";

    /**
     * Checks the database file and revises if there are tables inside. This is used to determine if
     * the database has its tables either created or cleared.
     *
     * @return true if the database is empty and false if it is not
     */
    public static boolean emptyDBChecker() {
        try (Connection conn = InitialiseDB.preparation()) {
            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet set = dbMeta.getTables(null, null, null, new String[]{"TABLE"});
            return !QueryDB.resultSetChecker(set);
        } catch (SQLException e) {
            System.out.println("There has been a problem with the SQL Code. Please try again");
        } catch (ClassNotFoundException e) {
            System.out.println("The drivers have not been loaded. Please try again");
        }
        return true;
    }

    /**
     * Standard getter for the SQL Script that clears the database file
     *
     * @return SQL Script that clears the database file
     */
    public static String getSqlDeletionScript() {
        return SQL_DELETION_SCRIPT;
    }

    /**
     * Initialises the drivers for SQLite. This code was given during class in the W06 Example Code.
     *
     * @throws ClassNotFoundException if for some reason, the drivers cannot be installed
     */
    public static void driverLoad() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    /**
     * Initialises the database file and also gives it its constraints. If this is not done, tells the
     * user the reason through messages.
     * If the tables were already initialised, the method also drops them and recreates them.
     * This is done to always start from scratch
     *
     * @param SQLscriptPath the path for the SQLScript (or any file that is supposed to take its role)
     * @return the correspondent message depending on the outcome
     */
    public static String initialiseDB(String SQLscriptPath) {

        File DDLScript = new File(SQLscriptPath);
        StringBuilder collector = new StringBuilder();
        if (!emptyDBChecker()) {
            if (new File(getSqlDeletionScript()).exists() && new File(getSqlDeletionScript()).canRead()) {
                try {
                    deleteDB(DDLTester.deletionTester(), new File(getSqlDeletionScript()));
                } catch (ClassNotFoundException e) {
                    return "The drivers for SQLite could not be loaded";
                } catch (SQLException e) {
                    return "There is a problem with the syntax of the SQL Deletion Script. Please check your SQL Code";
                } catch (IOException e) {
                    return "The SQL Deletion script cannot be read or it cannot be detected. Please try again";
                }
            } else {
                return "The tables were already initialised and therefore the program tried to clear the database. However, the SQL Deletion Script cannot be found. Please try again";
            }
        }

        if (DDLScript.canRead() && DDLScript.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(DDLScript))) {
                String line = reader.readLine();

                while (line != null) {
                    collector.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                return ("There has been a problem reading through the DDL Script. Please check the DDLScript");
            }

            ArrayList<String> commands =
                    new ArrayList<>(Arrays.asList(collector.toString().split(";")));

            if (commands.equals(DDLTester.tester())) {

                try {
                    driverLoad();

                    Connection conn = DriverManager.getConnection("jdbc:sqlite:" + getDatabaseFile());
                    Statement lecture = conn.createStatement();

                    for (String command : commands) {
                        lecture.executeUpdate(command);
                    }
                    lecture.close();
                    conn.close();
                    return ("OK");
                } catch (ClassNotFoundException e) {
                    return ("The drivers for SQLite could not be loaded");
                } catch (SQLException e) {
                    return ("There is a problem with the syntax of the DDL Script. Please check your SQL Code");
                }
            } else {
                return ("The DDL Script schema has not been validated. Therefore, the table was not created");
            }
        } else {
            return ("The DDL Script cannot be read or it cannot be detected. Please check the DDL Script");
        }

    }

    public static void main(String[] args) {

        System.out.println(InitialiseDB.initialiseDB(getSqlScript()));
    }

    /**
     * Initialises the SQLite drivers and also provides a connection to the database.
     *
     * @return a Connection object that is connected to the database.
     * @throws ClassNotFoundException if the drivers could not be loaded.
     * @throws SQLException           if there is an issue with the Database.
     */
    public static Connection preparation() throws ClassNotFoundException, SQLException {
        InitialiseDB.driverLoad();
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + getDatabaseFile());
        return connection;
    }

    /**
     * Clears the database from its expected tables and relations.
     *
     * @param perfectCommands provided by the DDLTester, these are the valid commands to clear the
     *                        database file.
     * @param SQLScript       ideally the TableClearDDL.sql file. However, it can be any file. Its purpose
     *                        is to contain the SQL Code to clear the database.
     * @throws ClassNotFoundException if the drivers could not be loaded
     * @throws SQLException           if the SQL Code has a syntax problem
     * @throws IOException            if the SQLScript parameter cannot be read for some reason
     */
    public static void deleteDB(List<String> perfectCommands, File SQLScript)
            throws ClassNotFoundException, SQLException, IOException {

        try (Connection conn = InitialiseDB.preparation()) {

            BufferedReader reader = new BufferedReader(new FileReader(SQLScript));
            Statement stmnt = conn.createStatement();
            List<String> testedCommands = reader.lines().map(line -> line.replaceAll(";", "")).toList();
            if (perfectCommands.equals(testedCommands)) {

                for (String command : testedCommands) {

                    stmnt.executeUpdate(command);
                }

            } else {

                System.out.println(
                        "The passed SQL Deletion Script does not meet the schema. Please try again");
            }

        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    /**
     * <p>
     * Standard getter for the database file name.
     * </p>
     *
     * @return the database file name.
     */

    public static String getDatabaseFile() {
        return DATABASE_FILE;
    }

    /**
     * Returns the SQL_Script filepath as a string
     *
     * @return the correct SQL_SCRIPT Filepath as a string
     */
    public static String getSqlScript() {
        return SQL_SCRIPT;
    }
}
