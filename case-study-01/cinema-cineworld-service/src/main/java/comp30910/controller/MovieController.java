package comp30910.controller;

import comp30910.model.Movie;
import comp30910.model.MovieRequest;
import comp30910.service.MovieService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/list")
    public List<Movie> list() {
        return movieService.findAll();
    }

    @PostMapping("/find")
    public Movie find(@RequestBody MovieRequest request) {
        return movieService.findByNameAndShowTime(request.getName(), request.getShowTime());
    }

    @GetMapping("/delay/{seconds}")
    public Map<String, ?> delay(@PathVariable int seconds) throws InterruptedException {
        return movieService.delay(seconds);
    }
}
