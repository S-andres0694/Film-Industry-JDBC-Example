import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom JSON Parsers that read through my created JSON Files to extract the data from
 * them and turn them into their correspondent objects. This is done through a series of streams,
 * which ultimately turn into lists of values for each column of the table (or it can also include
 * values for intermediate tables)
 *
 * @author 230018374
 */
public class Parsers {

  /**
   * This custom parser checks for a specific set of key-value pairs that match the different
   * columns that are in the Actors table.
   *
   * @param actorFile the passed file. Ideally it is the custom Actors JSON File, but it handles it
   *     if otherwise
   * @return returns an array of Actors objects with their values already assigned
   * @throws IOException if for some reason, the file cannot be read
   * @throws JSONSchemaException if the passed file does not meet the valid schema
   */
  public Actors[] actorParser(File actorFile) throws IOException, JSONSchemaException {
    BufferedReader reader = new BufferedReader(new FileReader(actorFile));
    ArrayList<String> nameList = new ArrayList<>(defaultStringParser(reader, "Full Name"));

    reader = new BufferedReader(new FileReader(actorFile));

    ArrayList<Integer> ageList = new ArrayList<>(defaultIntParser(reader, "Age"));

    reader = new BufferedReader(new FileReader(actorFile));

    ArrayList<String> birthdayList = new ArrayList<>(defaultStringParser(reader, "Birthday"));

    reader = new BufferedReader(new FileReader(actorFile));

    ArrayList<String> awardIDList = new ArrayList<>(defaultStringParser(reader, "Awards"));

    reader = new BufferedReader(new FileReader(actorFile));

    ArrayList<String> movieIDList = new ArrayList<>(defaultIDParser(reader, "Movies"));

    if (JSONTester.JSONActorsComparer(actorFile)) {
      Actors[] actors = new Actors[nameList.size()];

      for (int i = 0; i < actors.length; i++) {
        actors[i] = (new Actors(nameList.get(i), ageList.get(i), birthdayList.get(i)));

        if (awardIDList.get(i).contains(";")) {
          String[] array = awardIDList.get(i).split(";");

          for (String x : array) {
            actors[i].getAwardIDs().add(Integer.parseInt(x));
          }
        } else {
          if (Integer.parseInt(awardIDList.get(i)) > 0) {
            actors[i].getAwardIDs().add(Integer.parseInt(awardIDList.get(i)));
          }
        }

        if (movieIDList.get(i).contains(",")) {
          String[] array = movieIDList.get(i).split(",");

          for (String x : array) {
            actors[i].getMovieIDs().add(Integer.parseInt(x));
          }
        } else {
          if (Integer.parseInt(movieIDList.get(i)) > 0) {
            actors[i].getMovieIDs().add(Integer.parseInt(movieIDList.get(i)));
          }
        }
      }

      return actors;
    } else {
      throw new JSONSchemaException(
          "There has been a problem with the Actors JSON. It does not meet the valid schema requirements");
    }
  }

  /**
   * This custom parser checks for a specific set of key-value pairs that match the different
   * columns that are in the Awards table.
   *
   * @param awardsFile the passed file. Ideally it is the custom Awards JSON File, but it handles it
   *     if otherwise
   * @return returns an array of Actors objects with their values already assigned
   * @throws IOException if for some reason, the file cannot be read
   * @throws JSONSchemaException if the passed file does not meet the valid schema
   */
  public Awards[] awardsParser(File awardsFile) throws IOException, JSONSchemaException {
    BufferedReader reader = new BufferedReader(new FileReader(awardsFile));
    ArrayList<String> InstitutionList =
        new ArrayList<>(defaultStringParser(reader, "Award Institution"));

    reader = new BufferedReader(new FileReader(awardsFile));

    ArrayList<String> motiveList = new ArrayList<>(defaultStringParser(reader, "Motive"));

    if (JSONTester.JSONAwardsComparer(awardsFile)) {
      Awards[] awards = new Awards[InstitutionList.size()];

      for (int i = 0; i < awards.length; i++) {
        awards[i] = new Awards(InstitutionList.get(i), motiveList.get(i));
      }

      return awards;
    } else {
      throw new JSONSchemaException(
          "There has been a problem with the Awards JSON. It does not meet the valid schema requirements");
    }
  }

  /**
   * This custom parser checks for a specific set of key when the value is supposed to be a double
   *
   * @param key the specific key that the data is going to be parsed from
   * @param reader the passed reader. It is assigned to the file contained in the JSON
   *     Document Parser
   * @return returns a List of doubles retrieved from a specific key
   */
  public List<Double> defaultDoubleParser(BufferedReader reader, String key) {
    return new ArrayList<>(
        reader
            .lines()
            .filter(line -> line.startsWith("      " + "\"" + key + "\": "))
            .mapToDouble(
                line -> Double.parseDouble(line.replaceAll("      " + "\"" + key + "\": ", "")))
            .boxed()
            .toList());
  }

  /**
   * This custom parser checks for a specific set of key when the value is supposed to be an ID for
   * an entry in the table
   *
   * @param key the specific key that the data is going to be parsed from
   * @param reader the passed reader. It is assigned to the file contained in the JSON
   *     Document Parser
   * @return returns a List of strings which contain several IDs. This is later going to be split
   *     depending on the separation character used in the JSON
   */
  public List<String> defaultIDParser(BufferedReader reader, String key) {
    return reader
        .lines()
        .filter(line -> line.startsWith("      " + "\"" + key + "\": "))
        .map(line -> line.replaceAll("      " + "\"" + key + "\": ", ""))
        .map(line -> line.replaceAll("\"", ""))
        .toList();
  }

  /**
   * This custom parser checks for a specific set of key when the value is supposed to be an integer
   *
   * @param key the specific key that the data is going to be parsed from
   * @param reader the passed reader. It is assigned to the file contained in the JSON
   *     Document Parser
   * @return returns a List of integers retrieved from the document
   */
  public List<Integer> defaultIntParser(BufferedReader reader, String key) {
    return reader
        .lines()
        .filter(line -> line.startsWith("      " + "\"" + key + "\": "))
        .map(line -> (line.replaceAll("      " + "\"" + key + "\": ", "")))
        .map(line -> line.replaceAll(",", ""))
        .mapToInt(x -> Integer.parseInt(x))
        .boxed()
        .toList();
  }

  /**
   * This custom parser checks for a specific set of key when the value is supposed to be a string
   *
   * @param key the specific key that the data is going to be parsed from
   * @param reader the passed reader. It is assigned to the file contained in the JSON
   *     Document Parser
   * @return returns a List of strings retrieved from a specific key
   */
  public List<String> defaultStringParser(BufferedReader reader, String key) {
    return reader
        .lines()
        .filter(line -> line.startsWith("      " + "\"" + key + "\": "))
        .map(line -> line.replaceAll("      " + "\"" + key + "\": ", ""))
        .map(line -> line.replaceAll("[,\"]", ""))
        .toList();
  }

  /**
   * This custom parser checks for a specific set of key-value pairs that match the different
   * columns that are in the Awards table.
   *
   * @param directorFile the passed file. Ideally it is the custom Director JSON File, but it handles
   *     it if otherwise
   * @return returns an array of Director objects with their values already assigned
   * @throws IOException if for some reason, the file cannot be read
   * @throws JSONSchemaException if the passed file does not meet the valid schema
   */
  public Director[] directorsParser(File directorFile) throws IOException, JSONSchemaException {
    BufferedReader reader = new BufferedReader(new FileReader(directorFile));
    ArrayList<String> nameList = new ArrayList<>(defaultStringParser(reader, "Full Name"));

    reader = new BufferedReader(new FileReader(directorFile));

    ArrayList<Integer> movieCountList = new ArrayList<>(defaultIntParser(reader, "Movie Count"));

    reader = new BufferedReader(new FileReader(directorFile));

    ArrayList<String> awardIDList = new ArrayList<>(defaultIDParser(reader, "Awards"));
    Director[] directors = new Director[nameList.size()];

    if (JSONTester.JSONDirectorsComparer(directorFile)) {
      for (int i = 0; i < nameList.size(); i++) {
        directors[i] = new Director(nameList.get(i), movieCountList.get(i));

        if (awardIDList.get(i).contains(",")) {
          String[] array = awardIDList.get(i).split(",");

          for (String x : array) {
            directors[i].getAwardIDs().add(Integer.parseInt(x));
          }
        } else {
          if (Integer.parseInt(awardIDList.get(i)) > 0) {
            directors[i].getAwardIDs().add(Integer.parseInt(awardIDList.get(i)));
          }
        }
      }

      return directors;
    } else {
      throw new JSONSchemaException(
          "There has been a problem with the Directors JSON. It does not meet the valid schema requirements");
    }
  }

  /**
   * This custom parser checks for a specific set of key-value pairs that match the different
   * columns that are in the Genre table.
   *
   * @param genreFile the passed file. Ideally it is the custom Genre JSON File, but it handles it if
   *     otherwise
   * @return returns an array of Genre objects with their values already assigned
   * @throws IOException if for some reason, the file cannot be read
   * @throws JSONSchemaException if the passed file does not meet the valid schema
   */
  public Genre[] genreParser(File genreFile) throws IOException, JSONSchemaException {
    BufferedReader reader = new BufferedReader(new FileReader(genreFile));
    ArrayList<String> GenreNameList = new ArrayList<>(defaultStringParser(reader, "Genre"));

    reader.close();

    if (JSONTester.JSONGenresComparer(genreFile)) {
      Genre[] genres = new Genre[GenreNameList.size()];

      for (int i = 0; i < genres.length; i++) {
        genres[i] = new Genre(GenreNameList.get(i));
      }

      return genres;
    } else {
      throw new JSONSchemaException(
          "There has been a problem with the Genres JSON. It does not meet the valid schema requirements");
    }
  }

  /**
   * This custom parser checks for a specific set of key-value pairs that match the different
   * columns that are in the Movies table or related intermediate tables
   *
   * @param moviesFile the passed file. Ideally it is the custom Movies JSON File, but it handles it
   *     if otherwise
   * @return returns an array of Movies objects with their values already assigned
   * @throws IOException if for some reason, the file cannot be read
   * @throws JSONSchemaException if the passed file does not meet the valid schema
   */
  public Movies[] moviesParser(File moviesFile) throws IOException, JSONSchemaException {
    BufferedReader reader = new BufferedReader(new FileReader(moviesFile));
    ArrayList<String> TitleList = new ArrayList<>(defaultStringParser(reader, "Title"));

    reader = new BufferedReader(new FileReader(moviesFile));

    ArrayList<String> RDateList = new ArrayList<>(defaultStringParser(reader, "Release Date"));

    reader = new BufferedReader(new FileReader(moviesFile));

    ArrayList<String> RTime = new ArrayList<>(defaultStringParser(reader, "Running Time"));

    reader = new BufferedReader(new FileReader(moviesFile));

    ArrayList<String> genreList = new ArrayList<>(defaultStringParser(reader, "Genre"));

    reader = new BufferedReader(new FileReader(moviesFile));

    ArrayList<String> plotList = new ArrayList<>(defaultStringParser(reader, "Plot Summary"));

    reader = new BufferedReader(new FileReader(moviesFile));

    ArrayList<Integer> directorList = new ArrayList<>(defaultIntParser(reader, "Director"));

    reader = new BufferedReader(new FileReader(moviesFile));

    ArrayList<String> awardIDList = new ArrayList<>(defaultIDParser(reader, "Awards"));

    reader = new BufferedReader(new FileReader(moviesFile));

    ArrayList<String> ratingsIDList = new ArrayList<>(defaultStringParser(reader, "Ratings"));

    if (JSONTester.JSONMoviesComparer(moviesFile)) {
      Movies[] movies = new Movies[TitleList.size()];

      for (int i = 0; i < movies.length; i++) {
        movies[i] =
            new Movies(
                TitleList.get(i),
                RDateList.get(i),
                RTime.get(i),
                plotList.get(i),
                directorList.get(i));

        if (awardIDList.get(i).contains(",")) {
          String[] array = awardIDList.get(i).split(",");

          for (String x : array) {
            movies[i].getAwardIDs().add(Integer.parseInt(x));
          }
        } else {
          if (Integer.parseInt(awardIDList.get(i)) > 0) {
            movies[i].getAwardIDs().add(Integer.parseInt(awardIDList.get(i)));
          }
        }

        if (ratingsIDList.get(i).contains(";")) {
          String[] array = ratingsIDList.get(i).split(";");

          for (String x : array) {
            movies[i].getRatingsIDs().add(Integer.parseInt(x));
          }
        } else {
          if (Integer.parseInt(ratingsIDList.get(i)) > 0) {
            movies[i].getRatingsIDs().add(Integer.parseInt(ratingsIDList.get(i)));
          }
        }

        if (genreList.get(i).contains("-")) {
          String[] array = genreList.get(i).split("-");

          for (String x : array) {
            movies[i].getGenreIDs().add(Integer.parseInt(x));
          }
        } else {
          if (Integer.parseInt(genreList.get(i)) > 0) {
            movies[i].getGenreIDs().add(Integer.parseInt(genreList.get(i)));
          }
        }
      }

      return movies;
    } else {
      throw new JSONSchemaException(
          "There has been a problem with the Movies JSON. It does not meet the valid schema requirements");
    }
  }

  /**
   * This custom parser checks for a specific set of key-value pairs that match the different
   * columns that are in the Ratings table.
   *
   * @param ratingsFile the passed file. Ideally it is the custom Awards JSON File, but it handles it
   *     if otherwise
   * @return returns an array of Actors objects with their values already assigned
   * @throws IOException if for some reason, the file cannot be read
   * @throws JSONSchemaException if the passed file does not meet the valid schema
   */
  public Ratings[] ratingsParser(File ratingsFile) throws IOException, JSONSchemaException {
    BufferedReader reader = new BufferedReader(new FileReader(ratingsFile));
    ArrayList<String> CSourceList = new ArrayList<>(defaultStringParser(reader, "Critic Source"));

    reader = new BufferedReader(new FileReader(ratingsFile));

    ArrayList<Double> ratingsList = new ArrayList<>(defaultDoubleParser(reader, "Rating"));

    reader.close();

    if (JSONTester.JSONRatingsComparer(ratingsFile)) {
      Ratings[] ratings = new Ratings[CSourceList.size()];

      for (int i = 0; i < ratings.length; i++) {
        ratings[i] = new Ratings(CSourceList.get(i), ratingsList.get(i));
      }

      return ratings;
    } else {
      throw new JSONSchemaException(
          "There has been a problem with the Ratings JSON. It does not meet the valid schema requirements");
    }
  }
}
