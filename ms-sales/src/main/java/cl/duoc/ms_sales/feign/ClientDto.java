package cl.duoc.ms_sales.feign;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientDto {
    Long id;
    String rut;
    String primerNombre;
    String apellidoPaterno;
    String apellidoMaterno;
    String email;
    String telefono;
}
