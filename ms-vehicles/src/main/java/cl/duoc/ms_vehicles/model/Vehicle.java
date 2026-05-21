package cl.duoc.ms_vehicles.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicle")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "vin", nullable = false, length = 17)
    String vin;

    @Column(name = "brand", nullable = false, length = 50)
    String brand;

    @Column(name = "model", nullable = false, length = 60)
    String model;

    @Column(name = "year", nullable = false)
    Integer year;

    @Column(name = "color", nullable = false, length = 30)
    String color;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "status", nullable = false, length = 20)
    String status;

    @Column(name = "type", nullable = false, length = 30)
    String type;

    @Column(name = "mileage", nullable = false)
    Integer mileage;
}
