package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.vo.LoginVo;
import com.xxxx.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author richard
 * @since 2023-10-01
 */
public interface IUserService extends IService<User> {

    /**
     * 功能描述:登入
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     *功能描述:根據Cookie獲取用戶
     * @param //user
     * @return
     */
    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);

    /**
     * 更新密码
     *
     * @param userTicket
     * @param password
     * @return com.example.seckilldemo.vo.RespBean
     * @author LC
     * @operation add
     * @date 9:05 下午 2022/3/4
     **/
    default RespBean updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
