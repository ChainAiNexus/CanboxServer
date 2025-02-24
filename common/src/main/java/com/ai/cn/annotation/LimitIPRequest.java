package com.ai.cn.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)        //
public @interface LimitIPRequest {

    /**
     *
     * @Description:                    100
     * @return
     *
     */
    int limitCounts() default 1;

    /**
     *
     * @Description:                     1
     * @return
     */
    int timeSecond() default 60;

}
