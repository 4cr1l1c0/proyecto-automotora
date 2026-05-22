package cl.duoc.ms_employees.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    String lastName;

    @Column(name = "rut", nullable = false, length = 12)
    String rut;

    @Column(name = "email", nullable = false, length = 100)
    String email;

    @Column(name = "phone", nullable = false, length = 20)
    String phone;

    @Column(name = "position", nullable = false, length = 60)
    String position;

    @Column(name = "salary", nullable = false)
    Double salary;

    @Column(name = "hire_date", nullable = false)
    LocalDate hireDate;

    @Column(name = "active", nullable = false)
    Boolean active;

    @Column(name = "branch_id", nullable = false)
    Long branchId;
}
