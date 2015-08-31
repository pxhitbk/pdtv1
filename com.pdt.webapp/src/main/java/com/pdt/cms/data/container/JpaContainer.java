/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.data.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.UnsupportedFilterException;
import com.vaadin.data.util.sqlcontainer.RowId;

/**
 * Container that contain data collection of entities.
 *
 * @author hungpx
 *
 */
public class JpaContainer<T> implements Container, Container.Filterable, Container.Indexed, Container.Sortable,
		Container.ItemSetChangeNotifier {

	/** */
	private static final long serialVersionUID = 4670726799474868674L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaContainer.class);

	/** Maximumn number of items to cache */
	private static final int MAXIMUMN_CACHE_SIZE = 50;

	private final Class<T> type;
	/** Dynamic index table for sorting and filtering */
	private final Map<Integer, RowId> itemIndexes = new HashMap<Integer, RowId>();

	/** Store all column property ids */
	private final List<String> propertyIds = new ArrayList<String>();
	/** Mapper for property id and type */
	private final Map<String, Class<?>> propertyTypes = new HashMap<String, Class<?>>();
	/** Mapper property ids that is primary key */
	private final Map<String, Boolean> propertyPrimaryKey = new HashMap<String, Boolean>();

	/** List of all item id was contained in the container */
	private final List<Long> itemIds;
	private LoadingCache<Long, RowItem<T>> cache;

	private int pagingPointer = 0;

	/** Filters (WHERE) and sorters (ORDER BY) */
	private final List<Filter> filters = new ArrayList<Filter>();

	private final JpaContainerAdapter jpaContainerAdapter;

	public JpaContainer(Class<T> type, JpaContainerAdapter adapter) {
		this.type = type;
		this.jpaContainerAdapter = adapter;
		itemIds = adapter.getAllItemIds();
		buildCache();
	}

	/**
	 * Build cache for container
	 */
	private void buildCache() {
		cache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).maximumSize(MAXIMUMN_CACHE_SIZE)
				.build(new CacheLoader<Long, RowItem<T>>() {
					@SuppressWarnings("unchecked")
					@Override
					public RowItem<T> load(Long key) throws Exception {
						return jpaContainerAdapter.getAllRows();
					}
				});
		LOGGER.info(cache.size() + " entries were loaded to cache");
	}

	@Override
	public Item getItem(Object itemId) {
		RowItem<T> item = null;
		if (itemId == null || !(itemId instanceof Long))
			throw new IllegalArgumentException("itemId cannot be null or not type of Long");
		if (propertyPrimaryKey.containsKey(itemId))
			try {
				/* Get item from cache, if not present, cache will update by load method. */
				item = cache.get((Long) itemId);
			} catch (ExecutionException e) {
				LOGGER.error("Cannot load item of model " + getType().getName() + " with id = " + itemId, e);
			}
		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#getContainerPropertyIds()
	 */
	@Override
	public Collection<?> getContainerPropertyIds() {
		return propertyIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#getItemIds()
	 */
	@Override
	public Collection<?> getItemIds() {
		return itemIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#getContainerProperty(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Property<?> getContainerProperty(Object itemId, Object propertyId) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#getType(java.lang.Object)
	 */
	@Override
	public Class<?> getType(Object propertyId) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#containsId(java.lang.Object)
	 */
	@Override
	public boolean containsId(Object itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#addItem(java.lang.Object)
	 */
	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#addItem()
	 */
	@Override
	public Object addItem() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#removeItem(java.lang.Object)
	 */
	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		return false;
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue)
			throws UnsupportedOperationException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#removeContainerProperty(java.lang.Object)
	 */
	@Override
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#removeAllItems()
	 */
	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Ordered#nextItemId(java.lang.Object)
	 */
	@Override
	public Object nextItemId(Object itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Ordered#prevItemId(java.lang.Object)
	 */
	@Override
	public Object prevItemId(Object itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Ordered#firstItemId()
	 */
	@Override
	public Object firstItemId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Ordered#lastItemId()
	 */
	@Override
	public Object lastItemId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Ordered#isFirstId(java.lang.Object)
	 */
	@Override
	public boolean isFirstId(Object itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Ordered#isLastId(java.lang.Object)
	 */
	@Override
	public boolean isLastId(Object itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Ordered#addItemAfter(java.lang.Object)
	 */
	@Override
	public Object addItemAfter(Object previousItemId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Ordered#addItemAfter(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Item addItemAfter(Object previousItemId, Object newItemId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Sortable#sort(java.lang.Object[], boolean[])
	 */
	@Override
	public void sort(Object[] propertyId, boolean[] ascending) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Sortable#getSortableContainerPropertyIds()
	 */
	@Override
	public Collection<?> getSortableContainerPropertyIds() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Indexed#indexOfId(java.lang.Object)
	 */
	@Override
	public int indexOfId(Object itemId) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Indexed#getIdByIndex(int)
	 */
	@Override
	public Object getIdByIndex(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Indexed#getItemIds(int, int)
	 */
	@Override
	public List<?> getItemIds(int startIndex, int numberOfItems) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Indexed#addItemAt(int)
	 */
	@Override
	public Object addItemAt(int index) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Indexed#addItemAt(int, java.lang.Object)
	 */
	@Override
	public Item addItemAt(int index, Object newItemId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Filterable#addContainerFilter(com.vaadin.data.Container.Filter)
	 */
	@Override
	public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Filterable#removeContainerFilter(com.vaadin.data.Container.Filter)
	 */
	@Override
	public void removeContainerFilter(Filter filter) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Filterable#removeAllContainerFilters()
	 */
	@Override
	public void removeAllContainerFilters() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.Filterable#getContainerFilters()
	 */
	@Override
	public Collection<Filter> getContainerFilters() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param owner
	 */
	public void itemChangeNotification(RowItem<?> owner) {

	}

	public Class<T> getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.ItemSetChangeNotifier#addItemSetChangeListener(com.vaadin.data.Container.
	 * ItemSetChangeListener)
	 */
	@Override
	public void addItemSetChangeListener(ItemSetChangeListener listener) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.ItemSetChangeNotifier#addListener(com.vaadin.data.Container.ItemSetChangeListener)
	 */
	@Override
	public void addListener(ItemSetChangeListener listener) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Container.ItemSetChangeNotifier#removeItemSetChangeListener(com.vaadin.data.Container.
	 * ItemSetChangeListener)
	 */
	@Override
	public void removeItemSetChangeListener(ItemSetChangeListener listener) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.vaadin.data.Container.ItemSetChangeNotifier#removeListener(com.vaadin.data.Container.ItemSetChangeListener)
	 */
	@Override
	public void removeListener(ItemSetChangeListener listener) {
		// TODO Auto-generated method stub

	}
}
