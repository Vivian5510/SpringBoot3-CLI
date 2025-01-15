package com.rosy.framework.aspect;

import com.rosy.common.enums.ErrorCode;
import com.rosy.common.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {
    @Around("@annotation(com.rosy.common.annotation.ValidateRequest)")
    public Object validateRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (arg == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        return joinPoint.proceed();
    }
}
