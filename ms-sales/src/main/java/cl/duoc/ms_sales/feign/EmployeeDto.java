package cl.duoc.ms_sales.feign;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeDto {
    Long id;
    String firstName;
    String lastName;
    String rut;
    String position;
}
