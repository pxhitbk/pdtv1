/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.form;

import java.io.Serializable;

import com.vaadin.ui.FormLayout;

/**
 * @author august
 *
 */
public abstract class AbstractForm<T extends Serializable> extends FormLayout {
	/** */
	private static final long serialVersionUID = -2821073912525189519L;
	private T model;
	private Class<T> type;
	private boolean isModified;

	public AbstractForm(Class<T> type) throws InstantiationException, IllegalAccessException {
		this(type.newInstance(), type);
	}

	public AbstractForm(T model, Class<T> type) {
		this.model = model;
		this.type = type;
		init();
	}

	/**
	 * Initialize layout. Load on constructing object
	 */
	protected abstract void init();

	public boolean commit() {
		return false;
	}

	public void activate() {

	}

	public T getModel() {
		return model;
	}

	protected void setModel(T model) {
		this.model = model;
	}

	public Class<T> getType() {
		return type;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}
}
