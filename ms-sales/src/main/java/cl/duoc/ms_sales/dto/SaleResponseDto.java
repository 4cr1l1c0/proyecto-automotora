package cl.duoc.ms_sales.dto;

import cl.duoc.ms_sales.feign.ClientDto;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SaleResponseDto {

    Long id;
    LocalDate saleDate;
    Double subtotal;
    Double iva;
    Double total;
    String paymentType;
    Long clientId;
    Long vehicleId;
    Long employeeId;
    ClientDto client;
}
