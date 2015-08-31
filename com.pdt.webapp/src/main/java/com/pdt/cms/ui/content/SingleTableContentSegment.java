/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.content;

import static com.pdt.resources.Messages.get;

import com.pdt.cms.ui.AbstractContentSegment;
import com.pdt.util.Static;
import com.vaadin.data.util.AbstractContainer;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

/**
 * @author august
 *
 */
public abstract class SingleTableContentSegment<L extends AbstractLayout, C extends AbstractContainer> extends
		AbstractContentSegment<L> implements ClickListener {

	/** */
	private static final long serialVersionUID = 4497066170659991712L;

	protected static final String ADD_BUTTON_CAPTION = "Add";
	protected static final String EDIT_BUTTON_CAPTION = "Edit";
	protected static final String DELETE_BUTTON_CAPTION = "Delete";

	protected enum TableAction {
		ADD_ITEM, EDIT_ITEM, DELETE_ITEM;
		String caption;

		TableAction() {
			caption = get(Static.CMS_LANGUAGE, "AbstractContentSegment.Table_action_" + name());
		}

		String getCaption() {
			return caption;
		}
	}

	protected Table table;
	protected C container;

	public SingleTableContentSegment(L layout) {
		super(layout);
		// init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#init()
	 */
	@Override
	public void init() {
		container = initContainer();
		for (Column<?> c : getColumns()) {
			container.addContainerProperty(c.getId(), c.getType(), null);
		}

		/* Refresh data for table */
		refresh();

		HorizontalLayout actionBar = new HorizontalLayout();
		for (TableAction action : getActionButtons()) {
			Button b = new Button(action.getCaption());
			b.addClickListener(this);
			actionBar.addComponent(b);
		}

		getLayout().addComponent(actionBar);
		table = new Table(null, container);
		table.setPageLength(30);
		table.setImmediate(true);
		table.setSelectable(true);
		for (Column<?> c : getColumns()) {
			table.setColumnHeader(c.getId(), c.getName());
		}
		getLayout().addComponent(table);

		table.setWidth(get("TourManagementContent.table_width"));
		table.setHeight(get("TourManagementContent.table_height"));
	}

	/**
	 * Initialize container. This method was call when the content segment was first time initialize
	 *
	 * @return data container
	 */
	protected abstract C initContainer();

	/**
	 * Get order of content segment. By default order is 0. If parent container contains more than 1 content segments,
	 * the order should be set to each content segment.
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button b = event.getButton();
		Object itemId = table.getValue();
		if (TableAction.ADD_ITEM.getCaption().equals(b.getCaption())) {
			addItem();
		}
		if (TableAction.EDIT_ITEM.getCaption().equals(b.getCaption())) {
			editItem(itemId);
		}
		if (TableAction.DELETE_ITEM.getCaption().equals(b.getCaption())) {
			deleteItem(itemId);
		}
	}

	/**
	 * @param itemId
	 *            selected item id of container
	 */
	protected void deleteItem(Object itemId) {

	}

	/**
	 * @param itemId
	 *            selected item id of container
	 */
	protected void editItem(Object itemId) {

	}

	/**
	 * Override to implement add item to table
	 */
	protected void addItem() {

	}

	public C getContainer() {
		return container;
	}

	protected abstract Column<?>[] getColumns();

	/**
	 * Specify common action buttons: add, edit, delete.
	 *
	 * @return
	 */
	protected abstract TableAction[] getActionButtons();

	public static class Column<T> {
		int id;
		private String name;
		private Class<T> type;

		public Column(int id, String name, Class<T> type) {
			this.name = name;
			this.type = type;
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Class<T> getType() {
			return type;
		}

		public void setType(Class<T> type) {
			this.type = type;
		}
	}

	public class RefreshOnUpdate implements CloseListener {

		private static final long serialVersionUID = 7468215125983628068L;

		@Override
		public void windowClose(CloseEvent e) {
			refresh();
		}

	}
}
