package pl.recompiled.springstrongerpasswordsdemo.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PasswordListLoggingAspect {

    private static final ThreadLocal<Integer> added = ThreadLocal.withInitial(() -> 0);

    @Around("execution(Boolean pl.recompiled.springstrongerpasswordsdemo.password.validation.passwordlist.InternalFrequentPasswordListAdapter.addPassword(String))")
    public Object logPasswordIfSaved(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object result =  joinPoint.proceed();
        if (result instanceof Boolean saved && saved) {
            Object[] signatureArgs = joinPoint.getArgs();
            added.set(added.get() + 1);
            log.info("Added " + added.get() + ". password: " + signatureArgs[0]);
        }
        return result;
    }
}
