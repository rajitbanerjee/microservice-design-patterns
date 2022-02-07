package comp30910.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.TypeLiteral;
import comp30910.model.MoviesDocument;
import comp30910.repository.MoviesRepository;
import comp30910.utils.FileIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoviesService {
    private final MoviesRepository moviesRepository;
    private static final String DATA_FILE = "data/movies.json";

    public MoviesDocument findMovie(String name, String cinemaName) {
        return moviesRepository.findMovie(name, cinemaName);
    }

    public void initDatabase() {
        List<MoviesDocument> movies = new ArrayList<>();
        try {
            String jsonString = FileIO.readFileAsString(DATA_FILE);
            TypeLiteral<List<MoviesDocument>> type = new TypeLiteral<>() {};
            movies = JsonIterator.deserialize(jsonString, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        moviesRepository.deleteAll();
        moviesRepository.saveAll(movies);
        System.out.println("Movies saved!");
    }
}
