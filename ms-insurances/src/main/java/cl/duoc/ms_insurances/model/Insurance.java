package cl.duoc.ms_insurances.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "insurance")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "policy_number", nullable = false, length = 50, unique = true)
    String policyNumber;

    @Column(name = "type", nullable = false, length = 50)
    String type;

    @Column(name = "coverage_amount", nullable = false)
    BigDecimal coverageAmount;

    @Column(name = "premium", nullable = false)
    BigDecimal premium;

    @Column(name = "start_date", nullable = false)
    LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    LocalDate endDate;

    @Column(name = "vehicle_id", nullable = false)
    Long vehicleId;

    @Column(name = "client_id", nullable = false)
    Long clientId;

    @Column(name = "active", nullable = false)
    Boolean active;
}
