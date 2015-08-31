package com.pdt.cms.ui.content;

import static com.pdt.resources.Messages.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.pdt.cms.data.model.TourImageDto;
import com.pdt.cms.ui.dialog.TourManagementDialog;
import com.pdt.core.model.Image;
import com.pdt.core.model.Tour;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtTourService;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TourManagementContent extends SingleTableContentSegment<VerticalLayout, IndexedContainer> {

	public static Logger LOGGER = LoggerFactory.getLogger(TourManagementContent.class);

	private CoreService coreService = ServiceResolver.findService(CoreService.class);;

	/**
	 * @param layout
	 */
	public TourManagementContent() {
		super(new VerticalLayout());
	}

	/** */
	private static final long serialVersionUID = -8676951082364443879L;
	private static final Column<Long> ID_COLUMN = new Column<>(1, get("TourManagementContent.ID_COLUMN"), Long.class);
	private static final Column<String> TOUR_COLUMN = new Column<>(2, get("TourManagementContent.TOUR_COLUMN"),
			String.class);
	private static final Column<Integer> TOUR_RECOMMEND_ORDER = new Column<>(3,
			get("TourManagementContent.TOUR_RECOMMEND_ORDER_COLUMN"), Integer.class);
	private static final Column<Integer> TOUR_FAVOURITE_ORDER = new Column<>(4,
			get("TourManagementContent.TOUR_FAVOURITE_ORDER_COLUMN"), Integer.class);
	private static final Column<Date> BEGINDATE_COLUMN = new Column<>(5, get("TourManagementContent.BEGINDATE_COLUMN"),
			Date.class);
	private static final Column<Date> ENDDATE_COLUMN = new Column<>(6, get("TourManagementContent.ENDDATE_COLUMN"),
			Date.class);
	private static final Column<String> REGION = new Column<>(7, get("TourManagementContent.REGION"), String.class);
	private static final Column<Double> FROM_PRICE = new Column<>(8, get("TourManagementContent.FROM_PRICE"),
			Double.class);
	private static final Column<String> PRIORITY = new Column<>(9, get("TourManagementContent.PRIORITY"), String.class);

	private static final Column<?>[] COLUMNS = { ID_COLUMN, TOUR_COLUMN, TOUR_RECOMMEND_ORDER, TOUR_FAVOURITE_ORDER,
			BEGINDATE_COLUMN, ENDDATE_COLUMN, REGION, FROM_PRICE, PRIORITY };

	@Override
	public String getName() {
		return "Quan ly tour";
	}

	@Override
	public int getOrder() {
		return 0;
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
		ImmutableList<Tour> items = ImmutableList.copyOf(coreService.findAll(Tour.class));
		getContainer().removeAllItems();
		for (int i = 0; i < items.size(); i++) {
			Item item = container.addItem(items.get(i).getId());
			item.getItemProperty(ID_COLUMN.getId()).setValue(items.get(i).getId());
			item.getItemProperty(TOUR_COLUMN.getId()).setValue(items.get(i).getName());
			item.getItemProperty(TOUR_RECOMMEND_ORDER.getId()).setValue(items.get(i).getRecommendOrder());
			item.getItemProperty(TOUR_FAVOURITE_ORDER.getId()).setValue(items.get(i).getFavouriteOrder());
			item.getItemProperty(BEGINDATE_COLUMN.getId()).setValue(items.get(i).getBeginDate());
			item.getItemProperty(ENDDATE_COLUMN.getId()).setValue(items.get(i).getEndDate());
			item.getItemProperty(REGION.getId()).setValue(items.get(i).getRegionType().name());
			item.getItemProperty(FROM_PRICE.getId()).setValue(items.get(i).getFromPrice());
			item.getItemProperty(PRIORITY.getId()).setValue(items.get(i).getPriority().name());
		}

		if (table != null)
			table.setContainerDataSource(container);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#addItem()
	 */
	@Override
	protected void addItem() {
		UI.getCurrent().addWindow(
				new TourManagementDialog(TourImageDto.class, new TourImageDto(new Tour(), new ArrayList<Image>()),
						"Add new Tour", new RefreshOnUpdate()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#editItem(java.lang.Object)
	 */
	@Override
	protected void editItem(Object itemId) {
		if (itemId == null) {
			LOGGER.warn("Try to EDIT a tour with NULL itemId. RETURN, no action");
			return;
		}
		Tour t = coreService.findById(Tour.class, (Long) itemId);
		if (t != null) {
			List<Image> images = ServiceResolver.findService(PdtTourService.class).getImagesByTour(t.getId());
			TourImageDto dto = new TourImageDto(t, images);
			UI.getCurrent().addWindow(
					new TourManagementDialog(TourImageDto.class, dto, "Edit tour information", new RefreshOnUpdate()));
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
			LOGGER.warn("Try to DELETE a tour with NULL itemId. RETURN, no action", new IllegalStateException());
			return;
		}
		Tour t = coreService.findById(Tour.class, (Long) itemId);
		PdtTourService tservice = ServiceResolver.findService(PdtTourService.class);
		tservice.removeTour(t);
		refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.AbstractContentSegment#getActionButtons()
	 */
	@Override
	protected TableAction[] getActionButtons() {
		return new TableAction[] { TableAction.ADD_ITEM, TableAction.EDIT_ITEM, TableAction.DELETE_ITEM };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.AbstractContentSegment#initContainer()
	 */
	@Override
	protected IndexedContainer initContainer() {
		return new IndexedContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.AbstractContentSegment#getColumns()
	 */
	@Override
	protected Column<?>[] getColumns() {
		return COLUMNS;
	}

}
