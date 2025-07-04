package com.luispiquinrey.KnotCommerce.Service.Interface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.luispiquinrey.common_tools.LoginRequestDTO;

@FeignClient(path="/user/**")
public class AdministrationUsersFeign {
    @PostMapping("/login")
    LoginRequestDTO
}
