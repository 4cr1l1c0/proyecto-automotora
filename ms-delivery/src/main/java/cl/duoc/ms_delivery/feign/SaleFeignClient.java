package cl.duoc.ms_delivery.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-sales", url = "${feign.url.ms-sales}")
public interface SaleFeignClient {

    @GetMapping("/api/v1/sales/{id}")
    SaleDto findById(@PathVariable Long id);
}
