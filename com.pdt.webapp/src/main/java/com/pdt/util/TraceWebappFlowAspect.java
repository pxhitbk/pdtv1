package com.pdt.util;

import org.aspectj.lang.JoinPoint;

public class TraceWebappFlowAspect {
	private long start; 
	public void before(JoinPoint joinpoint) {
		start = System.currentTimeMillis();
		System.out.println("AOP INVOKED: " + joinpoint.getTarget().getClass().getName() + "." + joinpoint.getSignature().getName());
	}
	
	public void after(JoinPoint joinpoint) {
		System.out.println("AOP FINISHED (" + (System.currentTimeMillis() - start) + "ms)" + joinpoint.getTarget().getClass().getName() + "." + joinpoint.getSignature().getName());
	}
}
