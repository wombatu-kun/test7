package wombatukun.tests.test7.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {

	@Before("execution(* wombatukun.tests.test7.controllers.*.*(..))")
	public void logRequest(JoinPoint jp) {
		Logger log = LoggerFactory.getLogger(jp.getTarget().getClass());
		log.info("{}: {}", jp.getSignature().getName(), arguments(jp.getArgs()));
	}

	@AfterReturning(value = "execution(* wombatukun.tests.test7.controllers.*.*(..))", returning = "response")
	public void logResponse(JoinPoint jp, Object response) {
		Logger log = LoggerFactory.getLogger(jp.getTarget().getClass());
		log.info("{}: {}", jp.getSignature().getName(), response);
	}

	private String arguments(Object[] args){
		return (args!=null && args.length>0 ? args[0].toString() : "no params");
	}
}
