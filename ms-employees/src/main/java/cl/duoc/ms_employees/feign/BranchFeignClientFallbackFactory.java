package cl.duoc.ms_employees.feign;

import cl.duoc.ms_employees.exception.ServiceUnavailableException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BranchFeignClientFallbackFactory implements FallbackFactory<BranchFeignClient> {

    @Override
    public BranchFeignClient create(Throwable cause) {
        return id -> {
            if (cause instanceof FeignException fe && fe.status() >= 400 && fe.status() < 500) {
                return null;
            }
            log.error("Servicio ms-branches no disponible: {}", cause.getMessage());
            throw new ServiceUnavailableException("Servicio de sucursales no disponible");
        };
    }
}
