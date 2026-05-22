package cl.duoc.ms_test_drive.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TestDriveRequestDto {

    Long id;

    @NotNull Long clientId;
    @NotNull Long vehicleId;
    @NotNull LocalDate visitDate;
    String notes;
    @NotBlank @Size(max = 20) String status;
}
