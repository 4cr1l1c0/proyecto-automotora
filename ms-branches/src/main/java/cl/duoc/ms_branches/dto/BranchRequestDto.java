package cl.duoc.ms_branches.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BranchRequestDto {

    Long id;

    @NotBlank
    @Size(max = 100)
    String name;

    @NotBlank
    @Size(max = 150)
    String address;

    @NotBlank
    @Size(max = 20)
    String phone;

    @NotBlank
    @Size(max = 60)
    String region;

    @NotBlank
    @Size(max = 60)
    String city;

    @NotNull
    Boolean active;
}
