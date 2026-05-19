package cl.duoc.ms_employees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsEmployeesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEmployeesApplication.class, args);
    }
}
