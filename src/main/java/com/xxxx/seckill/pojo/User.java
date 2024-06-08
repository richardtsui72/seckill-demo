package com.xxxx.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author richard
 * @since 2023-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用戶ID, 手機號碼
     */
    private Long id;

    private String nickname;

    /**
     * MD5(MD5(pass明文+固定salt)+salt)
     */
    private String password;

    private String slat;

    /**
     * 頭像
     */
    private String head;

    /**
     * 註冊時間
     */
    private Date registerDate;

    /**
     * 最后一次登入時間
     */
    private Date lastLoginDate;

    /**
     * 登陸次數
     */
    private Integer loginCount;


}
