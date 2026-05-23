package cl.duoc.ms_insurances.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InsuranceRequestDto {

    Long id;

    @NotBlank(message = "El número de póliza no puede estar vacío")
    String policyNumber;

    @NotBlank(message = "El tipo de seguro no puede estar vacío")
    String type;

    @NotNull(message = "El monto de cobertura es requerido")
    @Positive(message = "El monto de cobertura debe ser mayor a 0")
    BigDecimal coverageAmount;

    @NotNull(message = "La prima es requerida")
    @Positive(message = "La prima debe ser mayor a 0")
    BigDecimal premium;

    @NotNull(message = "La fecha de inicio es requerida")
    LocalDate startDate;

    @NotNull(message = "La fecha de fin es requerida")
    LocalDate endDate;

    @NotNull(message = "El ID del vehículo es requerido")
    Long vehicleId;

    @NotNull(message = "El ID del cliente es requerido")
    Long clientId;

    @NotNull(message = "El estado activo es requerido")
    Boolean active;
}
