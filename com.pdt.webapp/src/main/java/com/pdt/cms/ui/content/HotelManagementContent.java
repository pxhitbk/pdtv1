/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.content;

import static com.pdt.resources.Messages.get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.pdt.cms.ui.dialog.HotelManagementDialog;
import com.pdt.core.model.Hotel;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtDestinationService;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public class HotelManagementContent extends SingleTableContentSegment<VerticalLayout, IndexedContainer> {
	/** */
	private static final long serialVersionUID = 3796746455883041457L;

	public static Logger LOGGER = LoggerFactory.getLogger(HotelManagementContent.class);
	private final CoreService coreService = ServiceResolver.findService(CoreService.class);

	private static final Column<Long> HOTEL_ID = new Column<>(1, "ID", Long.class);
	private static final Column<String> HOTEL_NAME = new Column<>(2, "Name", String.class);
	private static final Column<String> HOTEL_LEVEL = new Column<>(3, "Level", String.class);
	private static final Column<Double> FROM_PRICE = new Column<>(4, get("TourManagementContent.FROM_PRICE"),
			Double.class);
	private static final Column<Double> TO_PRICE = new Column<>(5, "To price", Double.class);
	private static final Column<String> ADDRESS = new Column<>(6, "Address", String.class);
	private static final Column<String> CITY = new Column<>(7, "City", String.class);
	private static final Column<?>[] COLUMNS = { HOTEL_ID, HOTEL_NAME, HOTEL_LEVEL, FROM_PRICE, TO_PRICE, ADDRESS, CITY };

	/**
	 * @param layout
	 */
	public HotelManagementContent() {
		super(new VerticalLayout());
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
	 * @see com.pdt.cms.ui.ContentSegment#getName()
	 */
	@Override
	public String getName() {
		return "Quan ly hotel";
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
		final PdtDestinationService service = ServiceResolver.findService(PdtDestinationService.class);
		ImmutableList<Hotel> items = ImmutableList.copyOf(service.getAllHotelWithCity());
		getContainer().removeAllItems();
		for (Hotel hotel : items) {

			Item item = container.addItem(hotel.getId());
			item.getItemProperty(HOTEL_ID.getId()).setValue(hotel.getId());
			item.getItemProperty(HOTEL_NAME.getId()).setValue(hotel.getName());
			item.getItemProperty(HOTEL_LEVEL.getId()).setValue(
					hotel.getHotelLevel() != null ? hotel.getHotelLevel().name() : "");
			item.getItemProperty(FROM_PRICE.getId()).setValue(hotel.getFromPrice());
			item.getItemProperty(TO_PRICE.getId()).setValue(hotel.getToPrice());
			item.getItemProperty(ADDRESS.getId()).setValue(hotel.getDetailAddress());
			item.getItemProperty(CITY.getId()).setValue(hotel.getCity().getCity());
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
		UI.getCurrent().addWindow(new HotelManagementDialog(new Hotel(), "Add new hotel", new RefreshOnUpdate()));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#editItem(java.lang.Object)
	 */
	@Override
	protected void editItem(Object itemId) {
		if (itemId == null) {
			LOGGER.warn("Try to EDIT a hotel with NULL id. RETURN, no action", new IllegalStateException());
			return;
		}

		Hotel hotel = coreService.findById(Hotel.class, (Long) itemId);
		if (hotel != null) {
			UI.getCurrent()
					.addWindow(new HotelManagementDialog(hotel, "Edit hotel information", new RefreshOnUpdate()));
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
			LOGGER.warn("Try to DELETE a hotel with NULL itemId. RETURN, no action", new IllegalStateException());
			return;
		}
		Hotel hotel = coreService.findById(Hotel.class, (Long) itemId);
		coreService.remove(hotel);
		refresh();
	}
}