package cl.duoc.ms_insurances.dto;

import cl.duoc.ms_insurances.feign.ClientDto;
import cl.duoc.ms_insurances.feign.VehicleDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InsuranceResponseDto {

    Long id;
    String policyNumber;
    String type;
    BigDecimal coverageAmount;
    BigDecimal premium;
    LocalDate startDate;
    LocalDate endDate;
    Long vehicleId;
    Long clientId;
    Boolean active;
    VehicleDto vehicle;
    ClientDto client;
}
