package com.spark.backend_payment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI backendPaymentOpenApi() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("ReSpark Payment API")
                    .description("ReSpark 결제 서비스 API")
                    .version("v1")
            );
    }
}
