package cl.duoc.ms_branches.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "branch")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false, length = 100)
    String name;

    @Column(name = "address", nullable = false, length = 150)
    String address;

    @Column(name = "phone", nullable = false, length = 20)
    String phone;

    @Column(name = "region", nullable = false, length = 60)
    String region;

    @Column(name = "city", nullable = false, length = 60)
    String city;

    @Column(name = "active", nullable = false)
    Boolean active;
}
