package cl.duoc.ms_sales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sale")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SaleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "sale_date", nullable = false)
    LocalDate saleDate;

    @Column(name = "subtotal", nullable = false)
    Double subtotal;

    @Column(name = "iva", nullable = false)
    Double iva;

    @Column(name = "total", nullable = false)
    Double total;

    @Column(name = "payment_type", nullable = false, length = 30)
    String paymentType;

    @Column(name = "client_id", nullable = false)
    Long clientId;

    @Column(name = "vehicle_id", nullable = false)
    Long vehicleId;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;
}
