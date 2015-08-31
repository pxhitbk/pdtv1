package com.pdt.cms.ui.content;

import static com.pdt.resources.Messages.get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.pdt.cms.ui.dialog.SubjectManagementDialog;
import com.pdt.core.model.TravelSubject;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtTourService;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class SubjectManagementContent extends SingleTableContentSegment<VerticalLayout, IndexedContainer> {

	public static Logger LOGGER = LoggerFactory.getLogger(SubjectManagementContent.class);

	private static final Column<Long> SUBJECT_ID_COLUMN = new Column<>(1, "ID", Long.class);

	private static final Column<String> SUBJECT_NAME_COLUMN = new Column<>(2,
			get("SubjectManagementContent.SUBJECT_NAME_COLUMN"), String.class);

	private static final Column<Boolean> SUBJECT_IS_STICK_COLUMN = new Column<>(3,
			get("SubjectManagementContent.SUBJECT_IS_STICK_COLUMN"), Boolean.class);

	private static final Column<String> SUBJECT_DESCRIPTION_COLUMN = new Column<>(4,
			get("SubjectManagementContent.SUBJECT_DESCRIPTION_COLUMN"), String.class);

	private static final Column<?>[] COLUMNS = { SUBJECT_ID_COLUMN, SUBJECT_NAME_COLUMN, SUBJECT_IS_STICK_COLUMN,
			SUBJECT_DESCRIPTION_COLUMN };

	public SubjectManagementContent() {
		super(new VerticalLayout());
	}

	/** */
	private static final long serialVersionUID = 3889357961355983109L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#getName()
	 */
	@Override
	public String getName() {
		return "Quan ly chu de";
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
		final CoreService service = ServiceResolver.findService(CoreService.class);
		ImmutableList<TravelSubject> items = ImmutableList.copyOf(service.findAll(TravelSubject.class));
		getContainer().removeAllItems();
		for (int i = 0; i < items.size(); i++) {
			Item item = container.addItem(items.get(i).getId());
			item.getItemProperty(SUBJECT_ID_COLUMN.getId()).setValue(items.get(i).getId());
			item.getItemProperty(SUBJECT_NAME_COLUMN.getId()).setValue(items.get(i).getName());
			item.getItemProperty(SUBJECT_IS_STICK_COLUMN.getId()).setValue(items.get(i).isStick());

			String desc = items.get(i).getDescription();
			if (desc != null)
				if (desc.length() > 60)
					desc = desc.substring(0, 60) + "...";
			item.getItemProperty(SUBJECT_DESCRIPTION_COLUMN.getId()).setValue(desc);
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
		UI.getCurrent().addWindow(
				new SubjectManagementDialog(new TravelSubject(), "Add new tour subject", new RefreshOnUpdate()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#editItem(java.lang.Object)
	 */
	@Override
	protected void editItem(Object itemId) {
		if (itemId == null) {
			LOGGER.warn("Try to EDIT a tour with NULL itemId. RETURN, no action", new IllegalStateException());
			return;
		}
		CoreService coreService = ServiceResolver.findService(CoreService.class);
		TravelSubject s = coreService.findById(TravelSubject.class, (Long) itemId);
		if (s != null) {
			UI.getCurrent()
					.addWindow(new SubjectManagementDialog(s, "Edit subject information", new RefreshOnUpdate()));
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
			LOGGER.warn("Try to DELETE a subject with NULL itemId. RETURN, no action", new IllegalStateException());
			return;
		}
		CoreService coreService = ServiceResolver.findService(CoreService.class);
		TravelSubject s = coreService.findById(TravelSubject.class, (Long) itemId);
		PdtTourService tourService = ServiceResolver.findService(PdtTourService.class);
		tourService.removeTravelSubject(s);
		refresh();
	}

}
