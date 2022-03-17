import comp30910.model.Movie;
import comp30910.model.MovieRequest;
import comp30910.model.Reservation;
import comp30910.utils.FileIO;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class Client {
    private static final int NUM_THREADS = 3;
    private static final int THREAD_INCR = 1;
    private static final int REQUESTS_PER_THREAD = 5;
    private static final String BASE_PATH = "results";
    private static final String HEADING = "(Thread, Request), Duration (nanosec)";
    private static final String HOST = "localhost:8080";
    private static int i;

    public static void main(String[] args) throws IOException {
        String filePath =
                String.format(
                        "%s/test-%s.csv", BASE_PATH, getCurrentTimestamp("yyyy-MM-dd-HH-mm-ss"));
        FileIO.writeToFile(filePath, HEADING, false);

        // Set up new load sources (threads)
        ExecutorService executor =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (i = 1; i <= NUM_THREADS; i += THREAD_INCR) {
            executor.execute(new LoadSource(i, REQUESTS_PER_THREAD, filePath));
        }
        executor.shutdown();
    }

    private static String getCurrentTimestamp(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    public static void makeRequest() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        String findMovieUrl = String.format("http://%s/movies/findMovie", HOST);

        String movieName = "Belfast";
        String cinemaName = "ODEON Stillorgan";
        HttpEntity<MovieRequest> movieRequest =
                new HttpEntity<>(new MovieRequest(movieName, cinemaName));

        Movie movie = restTemplate.postForObject(findMovieUrl, movieRequest, Movie.class);
        List<String> showTimes =
                movie.getShowTimes().stream()
                        .filter(showTime -> showTime.getCinemaName().equalsIgnoreCase(cinemaName))
                        .collect(Collectors.toList())
                        .get(0)
                        .getTimes();
        int randomElementIndex =
                ThreadLocalRandom.current().nextInt(showTimes.size()) % showTimes.size();
        HttpEntity<Reservation> reservationRequest =
                new HttpEntity<>(
                        new Reservation(
                                movieName,
                                cinemaName,
                                new Date(),
                                showTimes.get(randomElementIndex),
                                randomElementIndex,
                                "Gold",
                                12.00,
                                "John Doe",
                                "john.doe@xyz.com"));
    }

    // {
    // "movieName": "Belfast",
    // "cinemaName": "ODEON Stillorgan",
    // "date": "2022-02-07",
    // "time": "18:45",
    // "numTickets": 1,
    // "ticketCategory": "Gold",
    // "totalFarePaid": 12.00,
    // "clientName": "John Doe",
    // "clientEmail": "john.doe@xyz.com"
    // }
}

class LoadSource implements Runnable {
    int i;
    int requestsPerThread;
    String filePath;

    public LoadSource(int i, int requestsPerThread, String filePath) {
        this.i = i;
        this.requestsPerThread = requestsPerThread;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        // From each load source, spawn multiple requests
        ExecutorService executor =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int j = 1; j <= requestsPerThread; j++) {
            executor.execute(new Request(i, j, filePath));
        }
        executor.shutdown();
    }
}

class Request implements Runnable {
    int i;
    int j;
    String filePath;

    public Request(int i, int j, String filePath) {
        this.i = i;
        this.j = j;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            String threadIndex = String.format("\n(%d, %d)", i, j);
            long t1 = System.nanoTime();
            Client.makeRequest();
            long t2 = System.nanoTime();
            String content = String.format("%s, %d", threadIndex, t2 - t1);
            FileIO.writeToFile(filePath, content, true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
