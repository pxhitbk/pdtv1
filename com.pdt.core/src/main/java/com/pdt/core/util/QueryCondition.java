/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.util;

/**
 * @author august
 *
 */
public class QueryCondition {

	public enum Operator {
		EQ(" = "), NE(" <> "), GT(" > "), LT(" < "), LIKE(" like ");

		final String operatorString;

		/**
		 *
		 */
		private Operator(String op) {
			this.operatorString = op;
		}

		public String getOperatorString() {
			return operatorString;
		}

	};

	private final String property;
	private final Operator operator;
	private final String value;

	public QueryCondition(String property, Operator operator, String value) {
		this.property = property;
		this.operator = operator;
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public Operator getOperator() {
		return operator;
	}

	public String getValue() {
		return value;
	}
}
