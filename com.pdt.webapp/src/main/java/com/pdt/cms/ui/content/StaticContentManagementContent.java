/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.pdt.cms.ui.dialog.ContentManagementDialog;
import com.pdt.core.model.ContentType;
import com.pdt.core.model.StaticContent;
import com.pdt.core.service.CoreService;
import com.pdt.core.service.TourService;
import com.pdt.core.util.ServiceResolver;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public abstract class StaticContentManagementContent extends
		SingleTableContentSegment<VerticalLayout, IndexedContainer> {
	/** */
	private static final long serialVersionUID = 1390608891833954779L;
	/** */
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	private final CoreService coreService = ServiceResolver.findService(CoreService.class);

	private static final Column<Long> CONTENT_ID = new Column<>(1, "ID", Long.class);
	private static final Column<String> CONTENT_NAME = new Column<>(2, "Name", String.class);
	private final ContentType contentType;

	private static final Column<?>[] COLUMNS = { CONTENT_ID, CONTENT_NAME };

	/**
	 * @param layout
	 */
	public StaticContentManagementContent(ContentType type) {
		super(new VerticalLayout());
		this.contentType = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#init()
	 */
	@Override
	public void init() {
		super.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#refresh()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void refresh() {
		// synchronize with database
		final TourService service = ServiceResolver.findService(TourService.class);
		ImmutableList<StaticContent> items = ImmutableList.copyOf(service.getStaticContent(contentType));
		getContainer().removeAllItems();
		for (StaticContent content : items) {

			Item item = container.addItem(content.getId());
			item.getItemProperty(CONTENT_ID.getId()).setValue(content.getId());
			item.getItemProperty(CONTENT_NAME.getId()).setValue(content.getName());
		}

		if (table != null)
			table.setContainerDataSource(container);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#initContainer()
	 */
	@Override
	protected IndexedContainer initContainer() {
		return new IndexedContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#getColumns()
	 */
	@Override
	protected Column<?>[] getColumns() {
		return COLUMNS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#getActionButtons()
	 */
	@Override
	protected TableAction[] getActionButtons() {
		return new TableAction[] { TableAction.ADD_ITEM, TableAction.EDIT_ITEM, TableAction.DELETE_ITEM };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#addItem()
	 */
	@Override
	protected void addItem() {
		StaticContent newContent = new StaticContent();
		newContent.setContentType(getContentType());
		UI.getCurrent().addWindow(new ContentManagementDialog(newContent, "Add new content", new RefreshOnUpdate()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#editItem(java.lang.Object)
	 */
	@Override
	protected void editItem(Object itemId) {
		if (itemId == null) {
			logger.warn("Try to EDIT a content with NULL id. RETURN, no action", new IllegalStateException());
			return;
		}

		StaticContent content = coreService.findById(StaticContent.class, (Long) itemId);
		if (content != null) {
			UI.getCurrent().addWindow(new ContentManagementDialog(content, "Edit content", new RefreshOnUpdate()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#deleteItem(java.lang.Object)
	 */
	@Override
	protected void deleteItem(Object itemId) {
		if (itemId == null) {
			logger.warn("Try to DELETE a content with NULL itemId. RETURN, no action", new IllegalStateException());
			return;
		}
		StaticContent content = coreService.findById(StaticContent.class, (Long) itemId);
		coreService.remove(content);
		refresh();
	}

	public ContentType getContentType() {
		return contentType;
	}

}
