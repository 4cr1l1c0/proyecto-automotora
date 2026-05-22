package cl.duoc.ms_insurances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsInsurancesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsInsurancesApplication.class, args);
    }
}
