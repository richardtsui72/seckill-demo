package com.xxxx.seckill.utlis;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author: LC
 * @date 2022/3/2 5:46 下午
 * @ClassName: UUIDUtil
 */
@Slf4j
public class UUIDUtil {

    public static String uuid() {
        log.info("uuid():",UUID.randomUUID().toString().replace("-", ""));
        return UUID.randomUUID().toString().replace("-", "");
    }
}
