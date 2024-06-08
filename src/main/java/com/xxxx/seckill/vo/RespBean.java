package com.xxxx.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回對象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code;
    private String message;
    private Object object;

    /**
     * 功能描述:成功返回結果
     * @return
     */
    public static RespBean success(){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),null);
    }

    /**
     * 功能描述:成功返回結果
     * @return
     */

    public static RespBean success(Object object){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),object);
    }


    /**
     * 功能描述:失敗返回結果
     * @return
     */
    public static RespBean error (RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),null);
    }


    /**
     * 功能描述:失敗返回結果
     * @return
     */
    public static RespBean error (RespBeanEnum respBeanEnum,Object object){
        return  new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(), object);
    }

}
