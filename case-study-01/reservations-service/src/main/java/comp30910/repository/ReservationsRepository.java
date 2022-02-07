package comp30910.repository;

import comp30910.model.ReservationsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends MongoRepository<ReservationsDocument, String> {}
