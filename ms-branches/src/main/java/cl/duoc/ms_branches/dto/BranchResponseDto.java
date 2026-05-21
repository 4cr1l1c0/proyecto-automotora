package cl.duoc.ms_branches.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BranchResponseDto {

    Long id;
    String name;
    String address;
    String phone;
    String region;
    String city;
    Boolean active;
}
