package com.ai.cn.annotation;

/**
 * @author kires
 */
@FunctionalInterface
public
interface StatFunction<T> {

    T stat(T t1, T t2);

}