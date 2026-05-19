package cl.duoc.ms_clients.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientRequestDto {

    Long id;

    @NotBlank
    @Size(min = 9, max = 12)
    String rut;

    @NotBlank
    @Size(min = 2, max = 30)
    String primerNombre;

    @Size(max = 30)
    String segundoNombre;

    @NotBlank
    @Size(min = 2, max = 30)
    String apellidoPaterno;

    @NotBlank
    @Size(min = 2, max = 30)
    String apellidoMaterno;

    @NotBlank
    @Email(message = "Email debe ser un email válido")
    String email;

    @NotBlank
    String telefono;

    @NotBlank
    String direccion;

    @NotNull
    LocalDate fechaNacimiento;

    @NotNull
    Boolean activoCliente;
}
