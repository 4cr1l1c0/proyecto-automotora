package cl.duoc.ms_insurances.feign;

import cl.duoc.ms_insurances.exception.ServiceUnavailableException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientFeignClientFallbackFactory implements FallbackFactory<ClientFeignClient> {

    @Override
    public ClientFeignClient create(Throwable cause) {
        return id -> {
            if (cause instanceof FeignException fe && fe.status() >= 400 && fe.status() < 500) {
                return null;
            }
            log.error("Servicio ms-clients no disponible: {}", cause.getMessage());
            throw new ServiceUnavailableException("Servicio de clientes no disponible");
        };
    }
}
