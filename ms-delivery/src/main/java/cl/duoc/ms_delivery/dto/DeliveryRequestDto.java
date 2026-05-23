package cl.duoc.ms_delivery.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DeliveryRequestDto {

    Long id;

    @NotNull(message = "El ID de la venta es requerido")
    Long saleId;

    @NotNull(message = "El ID del cliente es requerido")
    Long clientId;

    @NotNull(message = "La fecha de entrega es requerida")
    LocalDate deliveryDate;

    @NotBlank(message = "La dirección de entrega no puede estar vacía")
    @Size(max = 150, message = "La dirección no puede superar 150 caracteres")
    String deliveryAddress;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede superar 20 caracteres")
    String status;

    String notes;
}
