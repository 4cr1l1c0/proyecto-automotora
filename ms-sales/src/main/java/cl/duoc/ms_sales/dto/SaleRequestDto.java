package cl.duoc.ms_sales.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SaleRequestDto {

    Long id;

    @NotNull(message = "La fecha de venta es requerida")
    LocalDate saleDate;

    @NotNull(message = "El subtotal es requerido")
    @Positive(message = "El subtotal debe ser mayor a 0")
    Double subtotal;

    @NotNull(message = "El IVA es requerido")
    @Positive(message = "El IVA debe ser mayor a 0")
    Double iva;

    @NotNull(message = "El total es requerido")
    @Positive(message = "El total debe ser mayor a 0")
    Double total;

    @NotBlank(message = "El tipo de pago no puede estar vacío")
    @Size(max = 30, message = "El tipo de pago no puede superar 30 caracteres")
    String paymentType;

    @NotNull(message = "El ID del cliente es requerido")
    Long clientId;

    @NotNull(message = "El ID del vehículo es requerido")
    Long vehicleId;

    @NotNull(message = "El ID del empleado es requerido")
    Long employeeId;
}
