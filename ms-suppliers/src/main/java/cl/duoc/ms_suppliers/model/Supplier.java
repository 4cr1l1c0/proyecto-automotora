package cl.duoc.ms_suppliers.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "supplier")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false, length = 100)
    String name;

    @Column(name = "rut", nullable = false, length = 12)
    String rut;

    @Column(name = "email", nullable = false, length = 100)
    String email;

    @Column(name = "phone", nullable = false, length = 20)
    String phone;

    @Column(name = "address", nullable = false, length = 150)
    String address;

    @Column(name = "contact_name", nullable = false, length = 100)
    String contactName;

    @Column(name = "active", nullable = false)
    Boolean active;
}
