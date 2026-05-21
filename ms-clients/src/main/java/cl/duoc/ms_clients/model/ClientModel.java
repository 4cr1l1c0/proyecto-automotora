package cl.duoc.ms_clients.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "client")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "rut", nullable = false, length = 12)
    String rut;

    @Column(name = "primer_nombre", nullable = false, length = 30)
    String primerNombre;

    @Column(name = "segundo_nombre", length = 30)
    String segundoNombre;

    @Column(name = "apellido_paterno", nullable = false, length = 30)
    String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false, length = 30)
    String apellidoMaterno;

    @Column(name = "email", nullable = false, length = 100)
    String email;

    @Column(name = "telefono", nullable = false, length = 9)
    String telefono;

    @Column(name = "direccion", nullable = false, length = 100)
    String direccion;

    @Column(name = "fecha_nacimiento", nullable = false)
    LocalDate fechaNacimiento;

    @Column(name = "activo_cliente", nullable = false)
    Boolean activoCliente;
}
