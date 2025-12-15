package com.KayraAtalay.manager;

import com.KayraAtalay.shared.response.RootEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(url = "http://localhost:9090/dev/v1/logi-track-auth", name = "userManager")
public interface UserManager {

    @GetMapping("/find-user-id-by-username")
    public RootEntity<Long> findUserIdByUsername();
}