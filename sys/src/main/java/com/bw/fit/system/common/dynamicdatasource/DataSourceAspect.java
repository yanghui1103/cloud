package com.bw.fit.system.common.dynamicdatasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author yangh
 * @Date 2019-1-23 11:26
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Aspect
@Order(-999)//保证该AOP在@Transactional之前执行
@Component
public class DataSourceAspect {
    @Before("execution(* com.bw.fit.*.*.service.impl.*.*(..))")
    public void before(JoinPoint point) {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        // 下面两个数组中，参数值和参数名的个数和位置是一一对应的。
        Object[] args = point.getArgs(); // 参数值
        String[] argNames = ((MethodSignature)point.getSignature()).getParameterNames(); // 参数名

        DynamicDataSourceHolder.putDataSourceKey("ds1");

    }
}
