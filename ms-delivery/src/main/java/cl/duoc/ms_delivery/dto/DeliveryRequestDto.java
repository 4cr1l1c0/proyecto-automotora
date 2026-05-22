package cl.duoc.ms_delivery.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DeliveryRequestDto {

    Long id;

    @NotNull Long saleId;
    @NotNull Long clientId;
    @NotNull LocalDate deliveryDate;
    @NotBlank @Size(max = 150) String deliveryAddress;
    @NotBlank @Size(max = 20) String status;
    String notes;
}
