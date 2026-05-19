package cl.duoc.ms_sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsSalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsSalesApplication.class, args);
    }
}
