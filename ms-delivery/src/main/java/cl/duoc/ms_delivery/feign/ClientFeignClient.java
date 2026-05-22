package cl.duoc.ms_delivery.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clients", url = "${feign.url.ms-clients}")
public interface ClientFeignClient {

    @GetMapping("/api/v1/clients/{id}")
    ClientDto findById(@PathVariable Long id);
}
