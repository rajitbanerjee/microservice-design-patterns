package comp30910.repository;

import comp30910.model.Movie;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    @Query("{'name': '?0', 'showTimes': '?2'}")
    Optional<Movie> findByNameAndShowTime(String name, String showTime);
}
