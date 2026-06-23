package cl.duoc.ms_delivery.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Entregas - Automotora")
                        .version("1.0")
                        .description("Documentación de la API para la gestión de entregas de vehículos")
                        .contact(new Contact().name("Equipo Automotora").email("contacto@automotora.cl"))
                        .license(new License().name("Uso interno Duoc UC")));
    }
}
