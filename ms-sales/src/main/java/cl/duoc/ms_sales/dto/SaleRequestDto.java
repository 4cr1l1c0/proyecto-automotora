package cl.duoc.ms_sales.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SaleRequestDto {

    Long id;

    @NotNull
    LocalDate saleDate;

    @NotNull
    @Positive
    Double subtotal;

    @NotNull
    @Positive
    Double iva;

    @NotNull
    @Positive
    Double total;

    @NotBlank
    @Size(max = 30)
    String paymentType;

    @NotNull
    Long clientId;

    @NotNull
    Long vehicleId;

    @NotNull
    Long employeeId;
}
