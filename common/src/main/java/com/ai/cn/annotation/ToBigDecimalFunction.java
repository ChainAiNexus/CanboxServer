package com.ai.cn.annotation;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
@FunctionalInterface
public interface ToBigDecimalFunction<T> {
    /**
     *
     * @param value
     * @return
     */
    BigDecimal applyAsBigDecimal(T value);
}
