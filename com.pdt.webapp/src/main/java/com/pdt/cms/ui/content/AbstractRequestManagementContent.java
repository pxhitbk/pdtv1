/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.content;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdt.cms.ui.customcomponent.NativeSelectEnum;
import com.pdt.cms.ui.dialog.RequestManagementDialog;
import com.pdt.cms.ui.util.FieldFactory;
import com.pdt.cms.ui.validator.NumericValidator;
import com.pdt.core.model.Request;
import com.pdt.core.model.RequestInfo;
import com.pdt.core.model.RequestStatus;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtRequestService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public abstract class AbstractRequestManagementContent<T extends Request> extends
		SingleTableContentSegment<VerticalLayout, IndexedContainer> implements ClickListener {

	/** */
	private static final long serialVersionUID = -4763677831370082186L;
	protected static final String HOTEL_SERVICE = "Hotel";
	protected static final String TOUR_SERVICE = "Tour";

	public Logger logger = LoggerFactory.getLogger(this.getClass());
	private final CoreService coreService = ServiceResolver.findService(CoreService.class);

	protected static final Column<Long> REQUEST_ID = new Column<>(1, "ID", Long.class);
	protected static final Column<String> CUSTOMER_NAME = new Column<>(2, "Khách hàng", String.class);
	protected static final Column<String> CUSTOMER_PHONE = new Column<>(3, "Phone", String.class);
	protected static final Column<String> CUSTOMER_EMAIL = new Column<>(4, "Email", String.class);
	protected static final Column<Date> CREATED_DATE = new Column<>(5, "Ngay yeu cau", Date.class);
	protected static final Column<Integer> NUMBER_OF_PERSON = new Column<>(6, "So nguoi", Integer.class);
	protected static final Column<String> EXPECTED_TIME = new Column<>(7, "Thời gian mong muốn", String.class);
	protected static final Column<String> PLAN_PERIOD = new Column<>(8, "Thời gian dự kiến", String.class);

	private TextField tfId = FieldFactory.createTextField(TextField.class, "Id", null, false, null);
	private TextField tfPaid = FieldFactory.createTextField(TextField.class, "Đã thanh toán", null, false, null);
	private TextField tfPlanedPrice = FieldFactory.createTextField(TextField.class, "Chi phí dự kiến", null, false,
			null);
	private TextField tfDealPrice = FieldFactory.createTextField(TextField.class, "Giá thỏa thuận", null, false, null);
	private TextArea txtNote = FieldFactory.createTextField(TextArea.class, "Ghi chú", null, false, null);
	private NativeSelectEnum<RequestStatus> slStatus = new NativeSelectEnum<RequestStatus>("Trạng thái",
			RequestStatus.values(), null);
	BeanFieldGroup<RequestInfo> bfg = new BeanFieldGroup<RequestInfo>(RequestInfo.class);
	private Button save = new Button("Lưu");
	private Button edit = new Button("Sua");

	private Class<T> type;
	private RequestInfo model = new RequestInfo();

	/**
	 * @param layout
	 */
	public AbstractRequestManagementContent(Class<T> requestType) {
		super(new VerticalLayout());
		type = requestType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#init()
	 */
	@Override
	public void init() {
		super.init();
		table.setHeight("350px");
		final FormLayout form = new FormLayout();
		form.setHeight("300px");
		getLayout().addComponent(form);

		NumericValidator<Double> doubleValidator = new NumericValidator<Double>(Double.class);
		tfPaid.addValidator(doubleValidator);
		tfPlanedPrice.addValidator(doubleValidator);
		tfDealPrice.addValidator(doubleValidator);

		// final BeanItem<RequestInfo> riFormBean = new BeanItem<RequestInfo>(model);

		bfg.bind(tfId, "id");
		bfg.bind(tfPaid, "paid");
		bfg.bind(tfPlanedPrice, "planedPrice");
		bfg.bind(tfDealPrice, "dealPrice");
		bfg.bind(slStatus, "status");
		bfg.bind(txtNote, "note");

		tfId.setVisible(false);
		tfPaid.setVisible(false);
		tfPlanedPrice.setVisible(false);
		tfDealPrice.setVisible(false);
		txtNote.setVisible(false);
		slStatus.setVisible(false);

		form.addComponent(tfId);
		form.addComponent(tfPlanedPrice);
		form.addComponent(tfDealPrice);
		form.addComponent(tfPaid);
		form.addComponent(slStatus);
		form.addComponent(txtNote);
		bfg.bindMemberFields(form);

		save.setVisible(false);
		edit.setVisible(false);
		form.addComponent(save);
		form.addComponent(edit);
		save.addClickListener(this);
		edit.addClickListener(this);
		setReadOnlyStatus(true);

		final PdtRequestService service = ServiceResolver.findService(PdtRequestService.class);

		table.addItemClickListener(new ItemClickListener() {

			/** */
			private static final long serialVersionUID = 5227633030754559112L;

			@Override
			public void itemClick(ItemClickEvent event) {
				Long reqId = (Long) event.getItemId();
				model = service.getRequestInfoByReqId(reqId);
				final BeanItem<RequestInfo> riFormBean = new BeanItem<RequestInfo>(model);
				bfg.setItemDataSource(riFormBean);
				tfId.setVisible(true);
				tfPaid.setVisible(true);
				tfPlanedPrice.setVisible(true);
				tfDealPrice.setVisible(true);
				txtNote.setVisible(true);
				slStatus.setVisible(true);
				setReadOnlyStatus(true);
				save.setVisible(false);
				edit.setVisible(true);
			}
		});
	}

	private void setReadOnlyStatus(boolean readOnly) {
		System.out.println(tfPaid.isReadOnly());
		tfPaid.setReadOnly(readOnly);
		tfPlanedPrice.setReadOnly(readOnly);
		tfDealPrice.setReadOnly(readOnly);
		txtNote.setReadOnly(readOnly);
		slStatus.setReadOnly(readOnly);
		System.out.println(tfPaid.isReadOnly());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		System.out.println(tfPaid.isReadOnly());
		if (save.equals(event.getButton())) {
			try {
				bfg.commit();
				coreService.update(model);

				save.setVisible(false);
				edit.setVisible(true);
				setReadOnlyStatus(true);

				Notification.show("Cập nhật thành công");
			} catch (CommitException e) {
				Notification.show("Thất bại, vui lòng thử lại");
			}
		} else if (edit.equals(event.getButton())) {
			save.setVisible(true);
			edit.setVisible(false);
			setReadOnlyStatus(false);
		}
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

	public abstract String getServiceColumnName();

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#getActionButtons()
	 */
	@Override
	protected TableAction[] getActionButtons() {
		return new TableAction[] { /* TableAction.ADD_ITEM, */TableAction.EDIT_ITEM, TableAction.DELETE_ITEM };
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#addItem()
	 */
	// @Override
	// protected void addItem() {
	// UI.getCurrent().addWindow(new HotelManagementDialog(new Hotel(), "Add new request", new RefreshOnUpdate()));
	// }

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#editItem(java.lang.Object)
	 */
	@Override
	protected void editItem(Object itemId) {
		if (itemId == null) {
			logger.warn("Try to EDIT a request with NULL id. RETURN, no action", new IllegalStateException());
			return;
		}

		T request = coreService.findById(type, (Long) itemId);
		if (request != null) {
			UI.getCurrent().addWindow(
					new RequestManagementDialog<T>(request, "Edit request information", new RefreshOnUpdate()));
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
			logger.warn("Try to DELETE a request with NULL itemId. RETURN, no action", new IllegalStateException());
			return;
		}
		T request = coreService.findById(type, (Long) itemId);
		coreService.remove(request);
		refresh();
	}

}
