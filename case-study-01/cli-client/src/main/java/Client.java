import com.google.common.collect.ImmutableMap;
import com.jsoniter.spi.TypeLiteral;
import comp30910.model.Cinema;
import comp30910.model.Movie;
import comp30910.model.Reservation;
import comp30910.util.FileIO;
import comp30910.util.RandomPicker;
import comp30910.util.RestClient;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Client {
    private static final int NUM_THREADS = 500;
    private static final int THREAD_INCR = 50;
    private static final int REQUESTS_PER_THREAD = 3000;
    private static final String BASE_PATH = "results";
    private static final String HEADING = "(Thread, Request), Duration (nanosec)";
    private static final String HOST = "http://localhost:8099";
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

    public static void makeRequest() {
        try {
            RestClient restClient = new RestClient();
            String clientName = "Jane Doe";
            String clientEmail = "jane.doe@ucd.ie";
            String date = "2022-03-17";
            List<Integer> numTickets = IntStream.range(1, 11).boxed().collect(Collectors.toList());
            Map<String, Double> ticketTypesAmounts =
                    ImmutableMap.of("Silver", 10.00, "Gold", 12.00, "Platinum", 15.00);

            String movieListUrl = HOST + "/api/movie/list";
            TypeLiteral<List<Cinema>> cinemaListType = new TypeLiteral<>() {};
            List<Cinema> cinemas = restClient.getForObject(movieListUrl, cinemaListType);

            Cinema randomCinema = RandomPicker.pickFromCollection(cinemas);
            Movie movie = RandomPicker.pickFromCollection(randomCinema.getMovies());
            String randomShowTime = RandomPicker.pickFromCollection(movie.getShowTimes());
            String cinemaName = randomCinema.getCinemaName();
            Integer tickets = RandomPicker.pickFromCollection(numTickets);
            String ticketType = RandomPicker.pickFromCollection(ticketTypesAmounts.keySet());
            Double amount = ticketTypesAmounts.get(ticketType) * tickets;

            Reservation body =
                    new Reservation(
                            clientName,
                            clientEmail,
                            movie.getId(),
                            date,
                            randomShowTime,
                            tickets,
                            ticketType,
                            amount);
            String reservationMakeUrl =
                    String.format("%s/cinema/%s/reservation/make", HOST, cinemaName);
            TypeLiteral<Reservation> reservationType = new TypeLiteral<>() {};
            Reservation reservation =
                    restClient.postForObject(reservationMakeUrl, body, reservationType);

            System.out.println(reservation);
        } catch (Exception e) {
            // Suspected: circuit broken
            System.out.println("Error: " + e.getMessage());
        }
    }
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
