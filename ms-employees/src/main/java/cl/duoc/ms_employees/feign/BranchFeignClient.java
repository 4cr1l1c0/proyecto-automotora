package cl.duoc.ms_employees.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-branches", url = "${feign.url.ms-branches}",
        fallbackFactory = BranchFeignClientFallbackFactory.class)
public interface BranchFeignClient {

    @GetMapping("/api/v1/branches/{id}")
    BranchDto findById(@PathVariable Long id);
}
