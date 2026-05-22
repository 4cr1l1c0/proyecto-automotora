package cl.duoc.ms_delivery.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "delivery")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "sale_id", nullable = false)
    Long saleId;

    @Column(name = "client_id", nullable = false)
    Long clientId;

    @Column(name = "delivery_date", nullable = false)
    LocalDate deliveryDate;

    @Column(name = "delivery_address", nullable = false, length = 150)
    String deliveryAddress;

    @Column(name = "status", nullable = false, length = 20)
    String status;

    @Column(name = "notes", columnDefinition = "TEXT")
    String notes;
}
