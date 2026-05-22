package cl.duoc.ms_test_drive.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "test_drive_visit")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TestDriveVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "client_id", nullable = false)
    Long clientId;

    @Column(name = "vehicle_id", nullable = false)
    Long vehicleId;

    @Column(name = "visit_date", nullable = false)
    LocalDate visitDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    String notes;

    @Column(name = "status", nullable = false, length = 20)
    String status;
}
