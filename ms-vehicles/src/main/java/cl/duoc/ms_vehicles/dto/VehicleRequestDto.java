package cl.duoc.ms_vehicles.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VehicleRequestDto {

    Long id;

    @NotBlank
    @Size(min = 17, max = 17, message = "VIN must be exactly 17 characters")
    String vin;

    @NotBlank
    @Size(max = 50)
    String brand;

    @NotBlank
    @Size(max = 60)
    String model;

    @NotNull
    @Min(1900)
    Integer year;

    @NotBlank
    @Size(max = 30)
    String color;

    @NotNull
    @Positive
    Double price;

    @NotBlank
    @Size(max = 20)
    String status;

    @NotBlank
    @Size(max = 30)
    String type;

    @NotNull
    @PositiveOrZero
    Integer mileage;
}
