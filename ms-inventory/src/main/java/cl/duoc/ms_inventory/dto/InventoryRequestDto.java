package cl.duoc.ms_inventory.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InventoryRequestDto {

    Long id;

    @NotNull Long vehicleId;
    @NotNull @PositiveOrZero Integer quantity;
    @NotNull @Positive Integer minQuantity;
    @NotBlank @Size(max = 100) String location;
    @NotNull LocalDate lastUpdated;
}
