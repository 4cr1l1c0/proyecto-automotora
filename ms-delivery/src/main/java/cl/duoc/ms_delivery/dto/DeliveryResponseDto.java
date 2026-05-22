package cl.duoc.ms_delivery.dto;

import cl.duoc.ms_delivery.feign.ClientDto;
import cl.duoc.ms_delivery.feign.SaleDto;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DeliveryResponseDto {

    Long id;
    Long saleId;
    Long clientId;
    LocalDate deliveryDate;
    String deliveryAddress;
    String status;
    String notes;
    SaleDto sale;
    ClientDto client;
}
