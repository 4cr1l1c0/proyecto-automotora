package cl.duoc.ms_suppliers.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SupplierRequestDto {

    Long id;

    @NotBlank
    @Size(max = 100)
    String name;

    @NotBlank
    @Size(min = 9, max = 12)
    String rut;

    @NotBlank
    @Email(message = "Email debe ser un email válido")
    String email;

    @NotBlank
    @Size(max = 20)
    String phone;

    @NotBlank
    @Size(max = 150)
    String address;

    @NotBlank
    @Size(max = 100)
    String contactName;

    @NotNull
    Boolean active;
}
