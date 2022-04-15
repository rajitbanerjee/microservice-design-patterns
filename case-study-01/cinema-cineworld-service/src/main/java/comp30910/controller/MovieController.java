package comp30910.controller;

import comp30910.model.Cinema;
import comp30910.model.Movie;
import comp30910.model.MovieRequest;
import comp30910.service.MovieService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private static final String SERVICE_PREFIX = "cinema-";
    private static final String SERVICE_SUFFIX = "-service";

    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("/list")
    public Cinema list() {
        String id = UUID.nameUUIDFromBytes(serviceName.getBytes()).toString();
        String cinemaName =
                serviceName.replaceFirst(SERVICE_PREFIX, "").replaceFirst(SERVICE_SUFFIX, "");
        List<Movie> movies = movieService.findAll();
        return new Cinema(id, cinemaName, movies);
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
