package cl.duoc.ms_branches;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsBranchesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsBranchesApplication.class, args);
    }
}
