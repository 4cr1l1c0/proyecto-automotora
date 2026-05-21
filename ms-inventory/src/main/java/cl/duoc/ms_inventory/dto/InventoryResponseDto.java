package cl.duoc.ms_inventory.dto;

import cl.duoc.ms_inventory.feign.VehicleDto;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InventoryResponseDto {

    Long id;
    Long vehicleId;
    Integer quantity;
    Integer minQuantity;
    String location;
    LocalDate lastUpdated;
    VehicleDto vehicle;
}
