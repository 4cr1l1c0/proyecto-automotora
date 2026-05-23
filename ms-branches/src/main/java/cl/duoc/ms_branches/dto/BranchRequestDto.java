package cl.duoc.ms_branches.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BranchRequestDto {

    Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    String name;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 150, message = "La dirección no puede superar 150 caracteres")
    String address;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 20, message = "El teléfono no puede superar 20 caracteres")
    String phone;

    @NotBlank(message = "La región no puede estar vacía")
    @Size(max = 60, message = "La región no puede superar 60 caracteres")
    String region;

    @NotBlank(message = "La ciudad no puede estar vacía")
    @Size(max = 60, message = "La ciudad no puede superar 60 caracteres")
    String city;

    @NotNull(message = "El estado activo es requerido")
    Boolean active;
}
