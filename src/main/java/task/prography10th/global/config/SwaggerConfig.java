package task.prography10th.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("프로그라피 10기 Spring Assignment")
                        .description("프로그라피 10기 Spring 과제 API 명세서")
                        .version("1.0.0"));
    }
}
