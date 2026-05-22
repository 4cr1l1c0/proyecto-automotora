package cl.duoc.ms_ensurances.feign;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientDto {
    Long id;
    String rut;
    String primerNombre;
    String segundoNombre;
    String apellidoPaterno;
    String apellidoMaterno;
    String email;
    String telefono;
    String direccion;
    LocalDate fechaNacimiento;
    Boolean activoCliente;
}
