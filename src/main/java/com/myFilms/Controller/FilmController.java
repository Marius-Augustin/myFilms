/* Class Controller (FilmController)
 @author Marius-Augustin Nițu
 @version 8th January 2025
*/

package com.myFilms.Controller;

import com.myFilms.Service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FilmController {
    @Autowired
    private FilmService filmManager;

    @GetMapping("/")
    public String startMain(Model model) {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String getMain(Model model,
                          @RequestParam(value = "display", required = false) String displayType) {
        filmManager.updateFilmArray();

        if (displayType != null)
            if (displayType.equals("sortByYear"))
                filmManager.sortByYear();
            else if (displayType.equals("sortByRating"))
                filmManager.sortByRating();

        model.addAttribute("filmArray", filmManager.getFilmArray());
        return "main";
    }

    @GetMapping("/add")
    public String displayUpdate(Model model) {
        return "add";
    }

    @PostMapping("/add")
    public String addFilm(@RequestParam("title") String title,
                          @RequestParam("releaseYear") int year,
                          @RequestParam("myRating") int rating,
                          Model model) {

        boolean isFound = filmManager.isJsonValid(title, year);
        if (filmManager.isJsonValid(title, year)) {
            filmManager.addFilm(title, year, rating);
            return "redirect:/main";
        } else {
            model.addAttribute("error", true);
            return "/add";
        }
    }

    @PostMapping("/sortYear")
    public String sortFilmsByYear() {
        return "redirect:/main?display=sortByYear";
    }

    @PostMapping("/sortRating")
    public String sortFilmsByRating() {
        return "redirect:/main?display=sortByRating";
    }

    @PostMapping("/delete")
    public String deleteFilm(@RequestParam("filmId") int myId) {
        filmManager.deleteFilm(myId);

        return "redirect:/main";
    }

    @GetMapping("/update")
    public String displayUpdate(@RequestParam("filmId") int myId,
                                Model model) {
        model.addAttribute("filmId", myId);

        return "update";
    }

    @PostMapping("/update")
    public String updateFilm(@RequestParam("filmId") int myId,
                             @RequestParam("title") String title,
                             @RequestParam("releaseYear") int year,
                             @RequestParam("myRating") int rating,
                             Model model) {
        boolean isFound = filmManager.isJsonValid(title, year);
        if (filmManager.isJsonValid(title, year)) {
            filmManager.updateFilm(myId, title, year, rating);
            return "redirect:/main";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("filmId", myId);
            return "/update";
        }
    }
}

