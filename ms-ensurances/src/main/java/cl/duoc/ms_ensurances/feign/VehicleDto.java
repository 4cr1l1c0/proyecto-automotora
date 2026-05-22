package cl.duoc.ms_ensurances.feign;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VehicleDto {
    Long id;
    String vin;
    String brand;
    String model;
    Integer year;
    String color;
    Double price;
    String status;
    String type;
    Integer mileage;
}
