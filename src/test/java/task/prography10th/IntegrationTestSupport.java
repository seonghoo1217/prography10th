package task.prography10th;

import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class IntegrationTestSupport {

    //    private static final String MYSQL_IMAGE = "mysql:8";
    private static final DockerImageName IMAGE_NAME = DockerImageName.parse("mysql:8.0.33");

    public static final String DATABASE_NAME = "prography10th";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "password";

//    private static final JdbcDatabaseContainer MYSQL;

    /*static {
        MYSQL = new MySQLContainer<>(MYSQL_IMAGE);

        MYSQL.start();
    }*/

    public static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>(IMAGE_NAME)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(USERNAME)
            .withPassword(PASSWORD);

    static {
        MYSQL_CONTAINER.start();
    }

    @PreDestroy
    public void stop() {
        MYSQL_CONTAINER.stop();
    }

    /*@DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", MYSQL_CONTAINER::getDriverClassName);
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.~username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
    }*/
}
