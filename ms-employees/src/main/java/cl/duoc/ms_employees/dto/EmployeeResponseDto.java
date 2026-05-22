package cl.duoc.ms_employees.dto;

import cl.duoc.ms_employees.feign.BranchDto;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeResponseDto {

    Long id;
    String firstName;
    String lastName;
    String rut;
    String email;
    String phone;
    String position;
    Double salary;
    LocalDate hireDate;
    Boolean active;
    Long branchId;
    BranchDto branch;
}
