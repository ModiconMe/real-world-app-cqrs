package com.modiconme.realworld.rest.feign.client;

import com.modiconme.realworld.operation.UserOperations;
import com.modiconme.realworld.rest.feign.rest.config.LocalFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user", path = "api/", configuration = LocalFeignConfig.class)
public interface UserClient extends UserOperations { }
