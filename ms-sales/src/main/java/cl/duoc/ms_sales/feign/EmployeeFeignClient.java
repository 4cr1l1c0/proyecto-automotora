package cl.duoc.ms_sales.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-employees", url = "${feign.url.ms-employees}",
        fallbackFactory = EmployeeFeignClientFallbackFactory.class)
public interface EmployeeFeignClient {

    @GetMapping("/api/v1/employees/{id}")
    EmployeeDto findById(@PathVariable Long id);
}
