package org.example.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.model.User;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.lang.Long.getLong;

@Aspect
@Component
@Log
public class LoggingAspect {
    @Around("execution(* org.example.service.UserService.saveUser(..))")
    @Order(1)
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        System.out.println("\nБудет выполнен метод " + "'" + methodName + "'" +
                ", имеющий параметры: " + Arrays.asList(arguments) + ".");
        System.out.println("Декорация метода начата.");

        User user = new User();
        user.setFirstName("Михаил");
        user.setLastName("Жванецкий:)))");

        Object[] newArguments = {user};
        Object returnedByMethod = joinPoint.proceed(newArguments);
        System.out.println("Декорация метода окончена.");

        return returnedByMethod;
    }

    @Around("@annotation(org.example.aspect.TrackUserAction)")
    @Order(2)
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println("Метод " + "'" + joinPoint.getSignature().getName() + "'"
                + " выполнен за " + executionTime + " мс.");
        return proceed;
    }
}
