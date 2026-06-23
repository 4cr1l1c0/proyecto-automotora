package cl.duoc.ms_sales.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clients", url = "${feign.url.ms-clients}",
        fallbackFactory = ClientFeignClientFallbackFactory.class)
public interface ClientFeignClient {

    @GetMapping("/api/v1/clients/{id}")
    ClientDto findById(@PathVariable Long id);
}
