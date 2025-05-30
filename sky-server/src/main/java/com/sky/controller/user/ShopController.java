package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "Shop related APIs")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";
    private RedisTemplate redisTemplate;


    @GetMapping("/status")
    @ApiOperation("Get the shop's status")
    public Result getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("The shop's status is {}", status == 1 ? "opening" : "closed");
        return Result.success(status);
    }
}
