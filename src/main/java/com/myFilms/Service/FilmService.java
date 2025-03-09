/* Class Service (FilmService) - manage the filmArray using the database.
 @author Marius-Augustin Nițu
 @version 8th January 2025
*/

package com.myFilms.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myFilms.Model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.List;

@Service
public class FilmService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private List<Film> filmArray;

    public void addFilm(String title, int year, int rating) {
        /* Insert into the database. */
        String sqlQuery = "insert into films (title, release_year, rating) values(?, ?, ?)";
        jdbcTemplate.update(sqlQuery, title, year, rating);
    }

    public void deleteFilm(int myId) {
        /* Delete from the database the film. */
        String sqlQuery = "delete from films where id = (?)";
        jdbcTemplate.update(sqlQuery, myId);
    }

    public void updateFilm(int myId, String title, int year, int rating) {
        String sqlQuery = "update films set title = (?), release_year = (?), rating = (?) where id = (?)";
        jdbcTemplate.update(sqlQuery, title, year, rating, myId);
    }

    public List<Film> createFilmArray() {
        /* Create the filmArray from the database. */
        String sqlQuery = "select * from films";

        return jdbcTemplate.query(sqlQuery, (ResultSet data, int rowNum) -> {
            String title = data.getString("title");
            int year = data.getInt("release_year");
            String myJson = getFilmJson(title, year);
            int id = data.getInt("id");
            int rating = data.getInt("rating");

            return new Film(id, myJson, rating);
        });
    }


    public String getFilmJson(String title, int year) {
        String urlString = "https://www.omdbapi.com/?apikey=48554341&t=%s&y=%d";
        urlString = String.format(urlString, title, year);

        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine; StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            return response.toString();

        } catch (Exception error) {
            error.printStackTrace();
        }
        return "Error";
    }

    public boolean isJsonValid(String title, int year) {
        String myJson = getFilmJson(title, year);
        String status = "False";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(myJson);
            status = rootNode.path("Response").asText();
        } catch (Exception error) {
            error.printStackTrace();
        }

        if (status.equals("True"))
            return true;
        return false;
    }

    public void updateFilmArray() {
        filmArray = createFilmArray();
    }

    public List<Film> getFilmArray() {
        return filmArray;
    }

    public void sortByYear() {
        filmArray.sort((first, second ) -> Integer.compare(second.getYear(), first.getYear()));
    }

    public void sortByRating() {
        filmArray.sort((first, second ) -> Integer.compare(second.getRating(), first.getRating()));
    }
}