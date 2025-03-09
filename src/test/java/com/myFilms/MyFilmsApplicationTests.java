/* Class Tests - manage jUnit tests.
 @author Marius-Augustin Nițu
 @version 8th January 2025
*/

package com.myFilms;

import com.myFilms.Model.Film;
import com.myFilms.Service.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MyFilmsApplicationTests {

	private FilmService filmManager = new FilmService();

	@BeforeEach
	void setUp() throws Exception {
		filmManager = new FilmService();
	}

	@Test
	@DisplayName("Ensure true Json response for my API calls.")
	void verifyTrueJsonResponse() {
		assertEquals(filmManager.isJsonValid("The Matrix", 1999), true);
		assertEquals(filmManager.isJsonValid("Fight Club", 1999), true);
		assertEquals(filmManager.isJsonValid("Se7en", 1995), true);
	}

	@Test
	@DisplayName("Ensure false Json response for my API calls.")
	void verifyFalseJsonResponse() {
		assertEquals(filmManager.isJsonValid("The Medrix", 1999), false);
		assertEquals(filmManager.isJsonValid("Fight Club", 1998), false);
		assertEquals(filmManager.isJsonValid("Se7en", 1994), false);
	}

	@Test
	@DisplayName("Correct film created from Json: The Matrix")
	void verifyFilmMatrix() {
		Film myFilm = new Film(1, filmManager.getFilmJson("The Matrix", 1999), 10);

		assertEquals(myFilm.getTitle(), "The Matrix");
		assertEquals(myFilm.getGenre(), "Action, Sci-Fi");
		assertEquals(myFilm.getActors(), "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss");

	}

	@Test
	@DisplayName("Correct film created from Json: Fight Club")
	void verifyFilmFightClub() {
		Film myFilm = new Film(1, filmManager.getFilmJson("Fight Club", 1999), 10);

		assertEquals(myFilm.getTitle(), "Fight Club");
		assertEquals(myFilm.getGenre(), "Drama");
		assertEquals(myFilm.getActors(), "Brad Pitt, Edward Norton, Meat Loaf");
	}
}
