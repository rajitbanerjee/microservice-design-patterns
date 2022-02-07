package comp30910.repository;

import comp30910.model.MoviesDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends MongoRepository<MoviesDocument, String> {

    @Query("{'movieName': '?0', 'showTimes.cinemaName': '?1'}")
    MoviesDocument findMovie(String movieName, String cinemaName);
}
