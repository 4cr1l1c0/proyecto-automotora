package cl.duoc.ms_test_drive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsTestDriveApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsTestDriveApplication.class, args);
    }
}
