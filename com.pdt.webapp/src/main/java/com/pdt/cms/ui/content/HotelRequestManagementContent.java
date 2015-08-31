/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.content;

import com.google.common.collect.ImmutableList;
import com.pdt.core.model.Customer;
import com.pdt.core.model.Hotel;
import com.pdt.core.model.HotelRequest;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtRequestService;
import com.vaadin.data.Item;

/**
 * @author august
 *
 */
public class HotelRequestManagementContent extends AbstractRequestManagementContent<HotelRequest> {

	/** */
	private static final long serialVersionUID = -821374472288421487L;

	private static final Column<String> HOTEL_NAME = new Column<String>(11, "Hotel", String.class);
	private static final Column<?>[] COLUMNS = new Column[] { REQUEST_ID, CUSTOMER_NAME, CUSTOMER_PHONE,
			CUSTOMER_EMAIL, HOTEL_NAME, EXPECTED_TIME, CREATED_DATE, NUMBER_OF_PERSON, EXPECTED_TIME, PLAN_PERIOD };

	/**
	 * @param requestType
	 */
	public HotelRequestManagementContent() {
		super(HotelRequest.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#refresh()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void refresh() {
		final PdtRequestService service = ServiceResolver.findService(PdtRequestService.class);
		ImmutableList<HotelRequest> items = ImmutableList.copyOf(service.getRequests(HotelRequest.class));
		getContainer().removeAllItems();
		for (HotelRequest req : items) {
			Customer c = req.getCustomer();
			Hotel h = req.getHotel();
			Item item = container.addItem(req.getId());
			item.getItemProperty(REQUEST_ID.getId()).setValue(req.getId());
			item.getItemProperty(CUSTOMER_NAME.getId()).setValue(c.getFullName());
			item.getItemProperty(CUSTOMER_PHONE.getId()).setValue(c.getPhone());
			item.getItemProperty(CUSTOMER_EMAIL.getId()).setValue(c.getEmail());
			item.getItemProperty(HOTEL_NAME.getId()).setValue(h.getName());
			item.getItemProperty(CREATED_DATE.getId()).setValue(req.getCreatedDate());
			item.getItemProperty(NUMBER_OF_PERSON.getId()).setValue(req.getTotalPerson());
			item.getItemProperty(EXPECTED_TIME.getId()).setValue(req.getExpectedTime());
			item.getItemProperty(PLAN_PERIOD.getId()).setValue(req.getPlannedPeriod());
		}

		if (table != null)
			table.setContainerDataSource(container);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#getName()
	 */
	@Override
	public String getName() {
		return "Đặt khách sạn";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.AbstractRequestManagementContent#getServiceColumnName()
	 */
	@Override
	public String getServiceColumnName() {
		return HOTEL_SERVICE;
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

}
