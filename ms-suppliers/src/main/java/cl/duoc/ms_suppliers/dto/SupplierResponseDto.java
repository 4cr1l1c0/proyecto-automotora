package cl.duoc.ms_suppliers.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SupplierResponseDto {

    Long id;
    String name;
    String rut;
    String email;
    String phone;
    String address;
    String contactName;
    Boolean active;
}
