/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.dialog;

import com.vaadin.data.Property;

/**
 * @author august
 *
 */
public class FormFieldProperty<T> implements Property<T> {
	/** */
	private static final long serialVersionUID = -4588903951220510637L;
	private final String fieldId;
	private Class<T> type;
	private String caption;
	private boolean required = false;
	private boolean readOnly = false;
	private T value;

	public FormFieldProperty(String fieldId, String caption, boolean required) {
		this.fieldId = fieldId;
		this.caption = caption;
		this.required = required;
	}

	public FormFieldProperty(final String fieldId, Class<T> fieldType, String caption, boolean required) {
		this.type = fieldType;
		this.caption = caption;
		this.required = required;
		this.fieldId = fieldId;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getCaption() {
		return caption;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Property#getValue()
	 */
	@Override
	public T getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Property#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(T newValue) throws com.vaadin.data.Property.ReadOnlyException {
		if (isReadOnly())
			throw new ReadOnlyException();
		this.value = newValue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Property#getType()
	 */
	@Override
	public Class<? extends T> getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Property#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Property#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean newStatus) {
		this.readOnly = newStatus;
	}
}
