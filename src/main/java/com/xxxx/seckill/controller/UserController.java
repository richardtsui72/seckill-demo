package com.xxxx.seckill.controller;


import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.rabbitmq.MQSender;
import com.xxxx.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author richard
 * @since 2023-10-01
 */
@Controller
@RequestMapping("/user")
public class UserController  {

    @Autowired
    private MQSender mqSender;

    /**
     * 功能描述:用戶信息(測試)
     * @param user
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info (User user){
        return RespBean.success(user);
    }

    /**
     * 功能描述:測試發送RabbitMQ消息
     */
//    @RequestMapping("/mq")
//    @ResponseBody
//    public void mq(){
//        mqSender.send("Hello");
//    }

    /**
     * 功能描述:Fanout 模式
     */
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public void mq01(){
//        mqSender.send("Hello");
//    }
    /**
     * 功能描述:Direct 模式
     */
//    @RequestMapping(value = "/mq/direct01")
//    @ResponseBody
//    public void mqDirect01() {
//        mqSender.send01("Hello Red");
//    }
    /**
     * 功能描述:Direct 模式
     */
//    @RequestMapping(value = "/mq/direct02")
//    @ResponseBody
//    public void mqDirect02() {
//        mqSender.send02("Hello Green");
//    }

    /**
     * 功能描述:Topic 模式
     */
//    @RequestMapping(value = "/mq/topic01")
//    @ResponseBody
//    public void mqtopic01() {
//        mqSender.send03("Hello Red");
//    }

    /**
     * 功能描述:Topic 模式
     */
//    @RequestMapping(value = "/mq/topic02")
//    @ResponseBody
//    public void mqtopic02() {
//        mqSender.send04("Hello Green");
//    }

    /**
     * 功能描述:Header 模式
     */
//    @RequestMapping(value = "/mq/header01")
//    @ResponseBody
//    public void header01() {
//        mqSender.send05("Hello 01");
//    }
//
//    @RequestMapping(value = "/mq/header02")
//    @ResponseBody
//    public void header02() {
//        mqSender.send06("Hello 02");
//    }

}
