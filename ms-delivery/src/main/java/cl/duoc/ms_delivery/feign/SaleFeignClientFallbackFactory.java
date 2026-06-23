package cl.duoc.ms_delivery.feign;

import cl.duoc.ms_delivery.exception.ServiceUnavailableException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaleFeignClientFallbackFactory implements FallbackFactory<SaleFeignClient> {

    @Override
    public SaleFeignClient create(Throwable cause) {
        return id -> {
            if (cause instanceof FeignException fe && fe.status() >= 400 && fe.status() < 500) {
                return null;
            }
            log.error("Servicio ms-sales no disponible: {}", cause.getMessage());
            throw new ServiceUnavailableException("Servicio de ventas no disponible");
        };
    }
}
