package cl.duoc.ms_inventory.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InventoryRequestDto {

    Long id;

    @NotNull(message = "El ID del vehículo es requerido")
    Long vehicleId;

    @NotNull(message = "La cantidad es requerida")
    @PositiveOrZero(message = "La cantidad debe ser mayor o igual a 0")
    Integer quantity;

    @NotNull(message = "La cantidad mínima es requerida")
    @Positive(message = "La cantidad mínima debe ser mayor a 0")
    Integer minQuantity;

    @NotBlank(message = "La ubicación no puede estar vacía")
    @Size(max = 100, message = "La ubicación no puede superar 100 caracteres")
    String location;

    @NotNull(message = "La fecha de última actualización es requerida")
    LocalDate lastUpdated;
}
