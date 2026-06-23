package cl.duoc.ms_test_drive.feign;

import cl.duoc.ms_test_drive.exception.ServiceUnavailableException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VehicleFeignClientFallbackFactory implements FallbackFactory<VehicleFeignClient> {

    @Override
    public VehicleFeignClient create(Throwable cause) {
        return id -> {
            if (cause instanceof FeignException fe && fe.status() >= 400 && fe.status() < 500) {
                return null;
            }
            log.error("Servicio ms-vehicles no disponible: {}", cause.getMessage());
            throw new ServiceUnavailableException("Servicio de vehículos no disponible");
        };
    }
}
