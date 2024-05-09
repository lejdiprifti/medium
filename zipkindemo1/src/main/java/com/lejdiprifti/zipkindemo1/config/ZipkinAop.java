package com.lejdiprifti.zipkindemo1.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import brave.ScopedSpan;
import brave.Tracer;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ZipkinAop {

	private static final String METHOD_NAME = "method.name";

	private final Tracer tracer;

	@Around("execution(public * com.lejdiprifti..*(..))")
	public Object traceAllMethodCalls(ProceedingJoinPoint pJoinPoint) throws Throwable {
		final MethodSignature methodSignature = (MethodSignature) pJoinPoint.getSignature();
		final ScopedSpan span = tracer.startScopedSpan(methodSignature.toShortString());
		if (span != null) {
			span.tag(METHOD_NAME, methodSignature.getName());
		}
		try {
			return pJoinPoint.proceed();
		} catch (Exception exception) {
			if (span != null) {
				span.error(exception);
			}
			throw exception;
		} finally {
			if (span != null) {
				span.finish();
			}
		}
	}
}