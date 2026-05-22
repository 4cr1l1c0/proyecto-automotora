package cl.duoc.ms_delivery.feign;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SaleDto {
    Long id;
    LocalDate saleDate;
    Double total;
    String paymentType;
    Long clientId;
    Long vehicleId;
    Long employeeId;
}
