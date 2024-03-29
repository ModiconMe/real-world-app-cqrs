package com.modiconme.realworld.rest.feign.client;

import com.modiconme.realworld.operation.ArticleOperations;
import com.modiconme.realworld.rest.feign.rest.config.LocalFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "article", path = "api/", configuration = LocalFeignConfig.class)
public interface ArticleClient extends ArticleOperations {
}
