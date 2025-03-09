/* Class Model (Film)
 @author Marius-Augustin Nițu
 @version 8th January 2025
*/

package com.myFilms.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Film {
    private int id;
    private String title;
    private int releaseYear;
    private int rating;
    private String genre;
    private String director;
    private String plot;
    private String actors;
    private String poster;

    public Film(int myId, String myJson, int myRating) {
        id = myId;
        rating = myRating;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(myJson);

            title = rootNode.path("Title").asText();
            releaseYear = rootNode.path("Year").asInt();
            genre = rootNode.path("Genre").asText();
            director = rootNode.path("Director").asText();
            plot = rootNode.path("Plot").asText();
            actors = rootNode.path("Actors").asText();
            poster = rootNode.path("Poster").asText();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public String toString() {
        return "<Id: " + id + ", title: " + title + ", releaseYear: " + releaseYear + ">";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return releaseYear;
    }

    public int getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getPlot() {
        return plot;
    }

    public String getActors() {
        return actors;
    }

    public String getPoster() {
        return poster;
    }
}
