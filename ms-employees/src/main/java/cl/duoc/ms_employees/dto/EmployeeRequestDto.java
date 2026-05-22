package cl.duoc.ms_employees.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeRequestDto {

    Long id;

    @NotBlank @Size(max = 50) String firstName;
    @NotBlank @Size(max = 50) String lastName;
    @NotBlank @Size(min = 9, max = 12) String rut;
    @NotBlank @Email String email;
    @NotBlank @Size(max = 20) String phone;
    @NotBlank @Size(max = 60) String position;
    @NotNull @Positive Double salary;
    @NotNull LocalDate hireDate;
    @NotNull Boolean active;
    @NotNull Long branchId;
}
