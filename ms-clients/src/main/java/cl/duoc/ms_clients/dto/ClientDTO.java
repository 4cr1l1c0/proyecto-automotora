package cl.duoc.ms_clients.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;

    @NotBlank(message = "El rut es obligatorio")
    @Size(min = 9, max = 12)
    private String rut;

    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(min = 2, max = 30)
    private String primerNombre;

    @Size(max = 30)
    private String segundoNombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(min = 2, max = 30)
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Size(min = 2, max = 30)
    private String apellidoMaterno;

    @NotBlank(message = "El email es obligatorio")
    @Email
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activoCliente;
}