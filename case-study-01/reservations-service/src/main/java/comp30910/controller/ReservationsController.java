package comp30910.controller;

import comp30910.model.ReservationsDocument;
import comp30910.service.ReservationsService;
import java.util.List;
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
@RequestMapping("/reservations")
public class ReservationsController {
    private final ReservationsService reservationsService;

    @PostMapping("/makeReservation")
    @ResponseStatus(HttpStatus.CREATED)
    public String makeReservation(@RequestBody ReservationsDocument request) {
        try {
            reservationsService.makeReservation(request);
            return "Reservation success!";
        } catch (Exception e) {
            throw new CouldNotMakeReservationException();
        }
    }

    @GetMapping("/getReservations")
    public List<ReservationsDocument> getReservations() {
        return reservationsService.getReservations();
    }

    @GetMapping("/delay/{seconds}")
    public Map<String, ?> delay(@PathVariable int seconds) throws InterruptedException {
        return reservationsService.delay(seconds);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class CouldNotMakeReservationException extends RuntimeException {
        static final long serialVersionUID = -6516152245823843037L;
    }
}
