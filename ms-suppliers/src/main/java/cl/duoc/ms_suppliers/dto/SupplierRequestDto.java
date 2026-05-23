package cl.duoc.ms_suppliers.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SupplierRequestDto {

    Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    String name;

    @NotBlank(message = "El RUT no puede estar vacío")
    @Size(min = 9, max = 12, message = "El RUT debe tener entre 9 y 12 caracteres")
    String rut;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 20, message = "El teléfono no puede superar 20 caracteres")
    String phone;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 150, message = "La dirección no puede superar 150 caracteres")
    String address;

    @NotBlank(message = "El nombre de contacto no puede estar vacío")
    @Size(max = 100, message = "El nombre de contacto no puede superar 100 caracteres")
    String contactName;

    @NotNull(message = "El estado activo es requerido")
    Boolean active;
}
