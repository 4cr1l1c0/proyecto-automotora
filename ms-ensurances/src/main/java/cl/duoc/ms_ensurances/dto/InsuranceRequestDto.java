package cl.duoc.ms_ensurances.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InsuranceRequestDto {

    Long id;

    @NotBlank
    String policyNumber;

    @NotBlank
    String type;

    @NotNull @Positive
    BigDecimal coverageAmount;

    @NotNull @Positive
    BigDecimal premium;

    @NotNull
    LocalDate startDate;

    @NotNull
    LocalDate endDate;

    @NotNull
    Long vehicleId;

    @NotNull
    Long clientId;

    @NotNull
    Boolean active;
}
