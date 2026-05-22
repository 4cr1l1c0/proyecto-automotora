package cl.duoc.ms_test_drive.dto;

import cl.duoc.ms_test_drive.feign.ClientDto;
import cl.duoc.ms_test_drive.feign.VehicleDto;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TestDriveResponseDto {

    Long id;
    Long clientId;
    Long vehicleId;
    LocalDate visitDate;
    String notes;
    String status;
    ClientDto client;
    VehicleDto vehicle;
}
