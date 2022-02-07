package comp30910.controller;

import comp30910.model.MoviesDocument;
import comp30910.service.MoviesService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MoviesController {
    private final MoviesService moviesService;

    @ResponseBody
    @PostMapping("/find")
    public MoviesDocument find(@RequestBody MovieRequest request) {
        MoviesDocument result = moviesService.findMovie(request.getName(), request.getCinemaName());
        if (result == null) throw new MovieNotFoundException();
        return result;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    class MovieNotFoundException extends RuntimeException {
        static final long serialVersionUID = -6516152229878843037L;
    }
}

@Data
class MovieRequest {
    private String name;
    private String cinemaName;
}
