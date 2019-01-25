package com.bw.fit.system.common.dynamicdatasource;

import com.bw.fit.system.common.entity.BaseEntity;
import com.bw.fit.system.common.model.BaseModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Description
 * @Author yangh
 * @Date 2019-1-23 11:26
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 *
 */
@Aspect
@Order(-999)//保证该AOP在@Transactional之前执行
@Component
public class DataSourceAspect {
    @Before("execution(* com.bw.fit.*.*.service.impl.*.*(..)) || execution(* com.bw.fit.*.*.mapper.*.*(..))")
    public void before(JoinPoint point) {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        // 下面两个数组中，参数值和参数名的个数和位置是一一对应的。
        Object[] args = point.getArgs(); // 参数值
        String[] argNames = ((MethodSignature)point.getSignature()).getParameterNames(); // 参数名
        for(Object object:args){
            if(object instanceof BaseModel){
                if("tenantcm001".equalsIgnoreCase(((BaseModel) object).getTenantId())){
                    DatabaseContextHolder.setDatabaseType(DatabaseType.tenantcm001);
                }else if("tenantcm002".equalsIgnoreCase(((BaseModel) object).getTenantId())){
                    DatabaseContextHolder.setDatabaseType(DatabaseType.tenantcm002);
                }
            }

            if(object instanceof BaseEntity){
                if("tenantcm001".equalsIgnoreCase(((BaseEntity) object).getTenant())){
                    DatabaseContextHolder.setDatabaseType(DatabaseType.tenantcm001);
                }else if("tenantcm002".equalsIgnoreCase(((BaseEntity) object).getTenant())){
                    DatabaseContextHolder.setDatabaseType(DatabaseType.tenantcm002);
                }
            }
        }

    }
}
