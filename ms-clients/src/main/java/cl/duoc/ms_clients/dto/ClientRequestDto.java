package cl.duoc.ms_clients.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientRequestDto {

    Long id;

    @NotBlank(message = "El RUT no puede estar vacío")
    @Size(min = 9, max = 12, message = "El RUT debe tener entre 9 y 12 caracteres")
    String rut;

    @NotBlank(message = "El primer nombre no puede estar vacío")
    @Size(min = 2, max = 30, message = "El primer nombre debe tener entre 2 y 30 caracteres")
    String primerNombre;

    @Size(max = 30, message = "El segundo nombre no puede superar 30 caracteres")
    String segundoNombre;

    @NotBlank(message = "El apellido paterno no puede estar vacío")
    @Size(min = 2, max = 30, message = "El apellido paterno debe tener entre 2 y 30 caracteres")
    String apellidoPaterno;

    @NotBlank(message = "El apellido materno no puede estar vacío")
    @Size(min = 2, max = 30, message = "El apellido materno debe tener entre 2 y 30 caracteres")
    String apellidoMaterno;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    String telefono;

    @NotBlank(message = "La dirección no puede estar vacía")
    String direccion;

    @NotNull(message = "La fecha de nacimiento es requerida")
    LocalDate fechaNacimiento;

    @NotNull(message = "El estado activo es requerido")
    Boolean activoCliente;
}
