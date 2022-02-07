package comp30910;

import comp30910.service.MoviesService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

@EnableEurekaClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        initDatabase(context);
    }

    private static void initDatabase(ConfigurableApplicationContext context) {
        context.getBean(MoviesService.class).initDatabase();
    }
}
