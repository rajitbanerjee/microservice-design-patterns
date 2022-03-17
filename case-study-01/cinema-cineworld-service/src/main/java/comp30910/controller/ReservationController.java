package comp30910.controller;

import comp30910.model.Reservation;
import comp30910.service.ReservationService;
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
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationsService;

    @PostMapping("/make")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation make(@RequestBody Reservation reservation) {
        return reservationsService.makeReservation(reservation);
    }

    @GetMapping("/list")
    public List<Reservation> list() {
        return reservationsService.findAll();
    }

    @GetMapping("/delay/{seconds}")
    public Map<String, ?> delay(@PathVariable int seconds) throws InterruptedException {
        return reservationsService.delay(seconds);
    }
}
