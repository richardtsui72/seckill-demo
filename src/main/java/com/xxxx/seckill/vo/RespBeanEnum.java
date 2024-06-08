package com.xxxx.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * 公共返回對象列舉
 */

@ToString
@AllArgsConstructor
@Getter
public enum RespBeanEnum {
    //通用
    SUCCESS(200,"SUCCESS") ,
    Error(500,"服務端異常") ,
    //登入模塊5002xx
    LOGIN_ERROR(500210,"用戶名或密碼不正確"),
    MOBILE_ERROR(500211,"手機號碼格式不正確"),
    BIND_ERROR(500212,"參數校驗異常:"),
    MOBILE_NOT_EXIST(500213, "手機號碼不存在"),
    PASSWORD_UPDATE_FAIL(500214,"密碼更新失敗"),
    SESSION_ERROR(500215,"用戶不存在"),
    //秒殺模塊5005xx
    EMPTY_STOCK(500500,"庫存不足"),
     REPEATE_ERROR(500501,"該商品每人限購一件");


    private final Integer code;
    private final String message;

}
