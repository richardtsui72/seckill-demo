package com.xxxx.seckill.validator;

/**
 * 驗證手機號
 */

import com.xxxx.seckill.vo.IsMobileValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {

    boolean  required() default  true;

    String message() default "手機號碼格式錯誤";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
