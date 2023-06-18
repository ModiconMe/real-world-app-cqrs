package com.modiconme.realworld.rest.feign.client;

import com.modiconme.realworld.operation.ProfileOperations;
import com.modiconme.realworld.rest.feign.rest.config.LocalFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "profile", path = "api/", configuration = LocalFeignConfig.class)
public interface ProfileClient extends ProfileOperations { }
