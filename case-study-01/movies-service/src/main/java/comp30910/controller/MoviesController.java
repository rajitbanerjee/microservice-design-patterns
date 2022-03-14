package comp30910.controller;

import comp30910.model.MovieRequest;
import comp30910.model.MoviesDocument;
import comp30910.service.MoviesService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MoviesController {
    private final MoviesService moviesService;

    @PostMapping("/findMovie")
    public MoviesDocument findMovie(@RequestBody MovieRequest request) {
        MoviesDocument result =
                moviesService.findMovie(request.getMovieName(), request.getCinemaName());
        if (result == null) throw new MovieNotFoundException();
        return result;
    }

    @GetMapping("/delay/{seconds}")
    public Map<String, ?> delay(@PathVariable int seconds) throws InterruptedException {
        return moviesService.delay(seconds);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class MovieNotFoundException extends RuntimeException {
        static final long serialVersionUID = -6516152229878843037L;
    }
}
