package task.prography10th;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Prography10thApplication {

    public static void main(String[] args) {
        SpringApplication.run(Prography10thApplication.class, args);
    }

}
