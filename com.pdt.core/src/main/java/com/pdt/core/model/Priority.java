package com.pdt.core.model;

public enum Priority {
	VERY_HIGH(1), HIGH(2), NORMAL(3), LOW(4), VERY_LOW(5);
	private final int order;
	private Priority(int priorityOrder) {
		this.order = priorityOrder;
	}
	
	public int getOrder() {
		return order;
	}
}
