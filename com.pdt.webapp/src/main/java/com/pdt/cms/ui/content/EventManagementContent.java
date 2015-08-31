package com.pdt.cms.ui.content;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.pdt.cms.ui.dialog.EventManagementDialog;
import com.pdt.core.model.EventScope;
import com.pdt.core.model.TravelEvent;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtTourService;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class EventManagementContent extends SingleTableContentSegment<VerticalLayout, IndexedContainer> {
	/** */
	private static final long serialVersionUID = -7518961184826345421L;

	public static Logger LOGGER = LoggerFactory.getLogger(TourManagementContent.class);
	private CoreService coreService = ServiceResolver.findService(CoreService.class);
	private PdtTourService tourService = ServiceResolver.findService(PdtTourService.class);

	private static final Column<Long> EVENT_ID = new Column<>(1, "ID", Long.class);
	private static final Column<String> EVENT_NAME = new Column<>(2, "Name", String.class);
	private static final Column<String> EVENT_SCOPE = new Column<>(3, "Scope", String.class);
	private static final Column<Date> BEGIN_DATE = new Column<>(4, "Begin date", Date.class);
	private static final Column<Date> END_DATE = new Column<>(5, "End date", Date.class);
	private static final Column<Long> NUM_OF_TOUR = new Column<>(6, "Tours", Long.class);

	private static final Column<?>[] COLUMNS = { EVENT_ID, EVENT_NAME, EVENT_SCOPE, BEGIN_DATE, END_DATE, NUM_OF_TOUR };

	/**
	 * @param container
	 */
	public EventManagementContent() {
		super(new VerticalLayout());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#getName()
	 */
	@Override
	public String getName() {
		return "Quan ly su kien";
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
		ImmutableList<TravelEvent> items = ImmutableList.copyOf(service.findAll(TravelEvent.class));
		getContainer().removeAllItems();
		for (int i = 0; i < items.size(); i++) {
			Item item = container.addItem(items.get(i).getId());
			item.getItemProperty(EVENT_ID.getId()).setValue(items.get(i).getId());
			item.getItemProperty(EVENT_NAME.getId()).setValue(items.get(i).getName());
			EventScope es = items.get(i).getEventScope();
			item.getItemProperty(EVENT_SCOPE.getId()).setValue(es != null ? es.name() : "");
			item.getItemProperty(BEGIN_DATE.getId()).setValue(items.get(i).getBeginDate());
			item.getItemProperty(END_DATE.getId()).setValue(items.get(i).getEndDate());

			Long numOfTours = tourService.countTourAssociationWithEvent(items.get(i).getId());
			item.getItemProperty(NUM_OF_TOUR.getId()).setValue(numOfTours);
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
		// TODO Auto-generated method stub
		return new IndexedContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#getColumns()
	 */
	@Override
	protected Column<?>[] getColumns() {
		// TODO Auto-generated method stub
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
				new EventManagementDialog(new TravelEvent(), "Add new tour event", new RefreshOnUpdate()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#editItem(java.lang.Object)
	 */
	@Override
	protected void editItem(Object itemId) {
		if (itemId == null) {
			LOGGER.warn("Try to EDIT a event with NULL id. RETURN, no action", new IllegalStateException());
			return;
		}

		TravelEvent event = coreService.findById(TravelEvent.class, (Long) itemId);
		if (event != null) {
			UI.getCurrent()
					.addWindow(new EventManagementDialog(event, "Edit event information", new RefreshOnUpdate()));
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
			LOGGER.warn("Try to DELETE a event with NULL itemId. RETURN, no action", new IllegalStateException());
			return;
		}
		TravelEvent event = coreService.findById(TravelEvent.class, (Long) itemId);
		PdtTourService tourService = ServiceResolver.findService(PdtTourService.class);
		tourService.removeTravelEvent(event);
		refresh();
	}

}
