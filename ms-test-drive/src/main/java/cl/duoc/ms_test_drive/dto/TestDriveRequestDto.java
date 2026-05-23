package cl.duoc.ms_test_drive.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TestDriveRequestDto {

    Long id;

    @NotNull(message = "El ID del cliente es requerido")
    Long clientId;

    @NotNull(message = "El ID del vehículo es requerido")
    Long vehicleId;

    @NotNull(message = "La fecha de visita es requerida")
    LocalDate visitDate;

    String notes;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede superar 20 caracteres")
    String status;
}
