/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.data.container;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.pdt.core.util.CommonValidation;
import com.vaadin.data.Property;

/**
 * @author hungpx
 * @param <T>
 *            property type
 *
 */
public class ColumnProperty<T> implements Property<T> {

	/** */
	private static final long serialVersionUID = -2467766364565451089L;
	private final String id;
	private String caption;
	private T value;
	private T changedValue;
	private final boolean isPrimaryKey;
	private final Class<T> type;
	private boolean isReadOnly = false;
	private boolean nullable = true;
	private boolean modified;
	private RowItem<?> owner;

	/**
	 * Construct a column property.
	 */
	public ColumnProperty(String id, String caption, T value, Class<T> type) {
		this(id, caption, value, type, false);
	}

	public ColumnProperty(String name, String caption, T value, Class<T> type, boolean isPrimaryKey) {
		this.id = name;
		if (caption == null) {
			this.caption = name;
		}
		this.value = value;
		this.isPrimaryKey = isPrimaryKey;
		this.type = type;
	}

	@Override
	public T getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(T newValue) throws com.vaadin.data.Property.ReadOnlyException {
		/* NOTE: this method is totally cloned from SQLContainer and should be checked overtime */
		if (!nullable) {
			CommonValidation.forceNotNull("Cannot set null value for not nullable column property", newValue);
		}
		if (isReadOnly) {
			throw new ReadOnlyException("Cannot set value for read-only property.");
		}

		/* Check if this property is a date property. */
		boolean isDateProperty = Time.class.equals(getType()) || Date.class.equals(getType())
				|| Timestamp.class.equals(getType());

		if (newValue != null) {
			/* Handle SQL dates, times and Timestamps given as java.util.Date */
			if (isDateProperty) {
				/*
				 * Try to get the millisecond value from the new value of this property. Possible type to convert from
				 * is java.util.Date.
				 */
				long millis = 0;
				if (newValue instanceof java.util.Date) {
					millis = ((java.util.Date) newValue).getTime();
					/*
					 * Create the new object based on the millisecond value, according to the type of this property.
					 */
					if (Time.class.equals(getType())) {
						newValue = (T) new Time(millis);
					} else if (Date.class.equals(getType())) {
						newValue = (T) new Date(millis);
					} else if (Timestamp.class.equals(getType())) {
						newValue = (T) new Timestamp(millis);
					}
				}
			}

			if (!getType().isAssignableFrom(newValue.getClass())) {
				throw new IllegalArgumentException("Illegal value type for ColumnProperty");
			}

			/*
			 * If the value to be set is the same that has already been set, do not set it again.
			 */
			if (isValueAlreadySet(newValue)) {
				return;
			}
		}

		/* Set the new value and notify container of the change. */
		changedValue = newValue;
		modified = true;
		owner.getContainer().itemChangeNotification(owner); // TODO: unchecked
		// Expected: container should be able to response this change)
	}

	private boolean isValueAlreadySet(Object newValue) {
		Object referenceValue = isModified() ? changedValue : value;

		return isNullable() && newValue == null && referenceValue == null || newValue.equals(referenceValue);
	}

	public void commit() {
		if (isModified()) {
			modified = false;
			value = changedValue;
		}
	}

	@Override
	public Class<? extends T> getType() {
		return type;
	}

	@Override
	public boolean isReadOnly() {
		return isReadOnly;
	}

	@Override
	public void setReadOnly(boolean newStatus) {
		isReadOnly = newStatus;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getId() {
		return id;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public RowItem<?> getOwner() {
		return owner;
	}

	public void setOwner(RowItem<?> owner) {
		this.owner = owner;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isModified() {
		return modified;
	}
}
