package cl.duoc.ms_employees.feign;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BranchDto {
    Long id;
    String name;
    String address;
    String city;
    String region;
}
