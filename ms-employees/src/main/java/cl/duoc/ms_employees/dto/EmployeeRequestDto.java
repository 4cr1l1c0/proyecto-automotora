package cl.duoc.ms_employees.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeRequestDto {

    Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    String firstName;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede superar 50 caracteres")
    String lastName;

    @NotBlank(message = "El RUT no puede estar vacío")
    @Size(min = 9, max = 12, message = "El RUT debe tener entre 9 y 12 caracteres")
    String rut;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 20, message = "El teléfono no puede superar 20 caracteres")
    String phone;

    @NotBlank(message = "El cargo no puede estar vacío")
    @Size(max = 60, message = "El cargo no puede superar 60 caracteres")
    String position;

    @NotNull(message = "El salario es requerido")
    @Positive(message = "El salario debe ser mayor a 0")
    Double salary;

    @NotNull(message = "La fecha de contratación es requerida")
    LocalDate hireDate;

    @NotNull(message = "El estado activo es requerido")
    Boolean active;

    @NotNull(message = "El ID de la sucursal es requerido")
    Long branchId;
}
