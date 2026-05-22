package cl.duoc.ms_inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "inventory_item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "vehicle_id", nullable = false)
    Long vehicleId;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @Column(name = "min_quantity", nullable = false)
    Integer minQuantity;

    @Column(name = "location", nullable = false, length = 100)
    String location;

    @Column(name = "last_updated", nullable = false)
    LocalDate lastUpdated;
}
