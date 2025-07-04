package com.luispiquinrey.KnotCommerce.Service.Interface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MicroservicesUsers", path = "/user")
public interface AdministrationUsersFeign {

    @GetMapping("/getUser/{id}")
    String getUserById(@PathVariable Long id);
}
