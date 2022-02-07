package comp30910.service;

import comp30910.model.ReservationsDocument;
import comp30910.repository.ReservationsRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationsService {
    private final ReservationsRepository reservationsRepository;

    public void makeReservation(ReservationsDocument request) {
        reservationsRepository.save(request);
    }

    public List<ReservationsDocument> getReservations() {
        return reservationsRepository.findAll();
    }

    public Map<String, ?> delay(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
        return new HashMap<>();
    }
}
