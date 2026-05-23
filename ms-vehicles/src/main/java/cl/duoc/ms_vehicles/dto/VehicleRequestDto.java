package cl.duoc.ms_vehicles.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VehicleRequestDto {

    Long id;

    @NotBlank(message = "El VIN no puede estar vacío")
    @Size(min = 17, max = 17, message = "El VIN debe tener exactamente 17 caracteres")
    String vin;

    @NotBlank(message = "La marca no puede estar vacía")
    @Size(max = 50, message = "La marca no puede superar 50 caracteres")
    String brand;

    @NotBlank(message = "El modelo no puede estar vacío")
    @Size(max = 60, message = "El modelo no puede superar 60 caracteres")
    String model;

    @NotNull(message = "El año es requerido")
    @Min(value = 1900, message = "El año debe ser mayor o igual a 1900")
    Integer year;

    @NotBlank(message = "El color no puede estar vacío")
    @Size(max = 30, message = "El color no puede superar 30 caracteres")
    String color;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser mayor a 0")
    Double price;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede superar 20 caracteres")
    String status;

    @NotBlank(message = "El tipo no puede estar vacío")
    @Size(max = 30, message = "El tipo no puede superar 30 caracteres")
    String type;

    @NotNull(message = "El kilometraje es requerido")
    @PositiveOrZero(message = "El kilometraje debe ser mayor o igual a 0")
    Integer mileage;
}
