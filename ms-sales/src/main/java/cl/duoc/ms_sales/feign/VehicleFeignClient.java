package cl.duoc.ms_sales.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-vehicles", url = "${feign.url.ms-vehicles}")
public interface VehicleFeignClient {

    @GetMapping("/api/v1/vehicles/{id}")
    VehicleDto findById(@PathVariable Long id);
}
