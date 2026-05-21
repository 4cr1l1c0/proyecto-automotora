package cl.duoc.ms_vehicles.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VehicleResponseDto {

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
