package com.pdt.cms.data.container;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

/**
 * @param <T>
 *            the type of model object
 * @author hungpx
 *
 */
public class RowItem<T> implements Item {

	/** */
	private static final long serialVersionUID = 7033559711394861745L;

	private Long id;
	private final List<ColumnProperty<?>> properties;
	private final Class<T> type;
	private final JpaContainer<T> container;

	public RowItem(Long id, List<ColumnProperty<?>> props, JpaContainer<T> container, Class<T> type) {
		this.id = id;
		this.properties = props;
		this.type = type;
		this.container = container;
	}

	/**
	 * Get unique row id. Basically, this should the primary key of entity object or dto view model .
	 *
	 * @return
	 */
	public Long getRowId() {
		return id;
	}

	public Class<T> getType() {
		return type;
	}

	@Override
	public Property<?> getItemProperty(Object id) {
		if (id instanceof String && id != null) {
			for (ColumnProperty<?> cp : properties) {
				if (id.equals(cp.getId())) {
					return cp;
				}
			}
		}
		return null;
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		List<String> ids = Lists.transform(properties, new Function<ColumnProperty<?>, String>() {

			@Override
			public String apply(ColumnProperty<?> input) {
				return input.getId();
			}
		});
		return ids;
	}

	public JpaContainer<T> getContainer() {
		return container;
	}

	@Override
	public boolean addItemProperty(Object id, @SuppressWarnings("rawtypes") Property property)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
