/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.content;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.pdt.cms.ui.dialog.SlideImagesManagementDialog;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.webapp.model.HomePageImage;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public class SlideImagesManagementContent extends SingleTableContentSegment<VerticalLayout, IndexedContainer> {
	/** */
	private static final long serialVersionUID = 3796746455883041457L;

	public static Logger LOGGER = LoggerFactory.getLogger(TourManagementContent.class);
	private final CoreService coreService = ServiceResolver.findService(CoreService.class);

	private static final Column<Long> IMAGE_ID = new Column<>(1, "ID", Long.class);
	private static final Column<String> IMAGE_NAME = new Column<>(2, "Name", String.class);
	private static final Column<String> LINK = new Column<>(2, "Link", String.class);
	private static final Column<String> LINK_CAPTION = new Column<>(2, "Link caption", String.class);
	private static final Column<?>[] COLUMNS = { IMAGE_ID, IMAGE_NAME, LINK, LINK_CAPTION };

	/**
	 * @param layout
	 */
	public SlideImagesManagementContent() {
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
		table.setHeight("300px");
		final Embedded image = new Embedded("Uploaded Image");
		image.setHeight("300px");
		getLayout().addComponent(image);
		table.addItemClickListener(new ItemClickListener() {

			/** */
			private static final long serialVersionUID = 1316137547757917079L;

			@Override
			public void itemClick(ItemClickEvent event) {
				System.out.println(event.getItemId().toString());
				Long id = (Long) event.getItemId();
				HomePageImage im = coreService.findById(HomePageImage.class, id);

				final byte[] imgData = im.getData();
				if (imgData != null) {
					StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
						private static final long serialVersionUID = -7924008988643152334L;

						@Override
						public InputStream getStream() {
							return new ByteArrayInputStream(imgData);
						}
					};

					StreamResource imageResource = new StreamResource(imageSource, im.getFileName());
					imageResource.setCacheTime(1000);
					image.setSource(imageResource);
					// image.requestRepaint();
					imageResource.setFilename(im.getFileName());
				}
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#getName()
	 */
	@Override
	public String getName() {
		return "Quan ly hinh anh trang chu";
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
		ImmutableList<HomePageImage> items = ImmutableList.copyOf(service.findAll(HomePageImage.class));
		getContainer().removeAllItems();
		for (int i = 0; i < items.size(); i++) {
			Item item = container.addItem(items.get(i).getId());
			item.getItemProperty(IMAGE_ID.getId()).setValue(items.get(i).getId());
			item.getItemProperty(IMAGE_NAME.getId()).setValue(items.get(i).getName());
			item.getItemProperty(LINK.getId()).setValue(items.get(i).getLink());
			item.getItemProperty(LINK_CAPTION.getId()).setValue(items.get(i).getLinkCaption());
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
				new SlideImagesManagementDialog(new HomePageImage(), "Add new image for homepage",
						new RefreshOnUpdate()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.content.SingleTableContentSegment#editItem(java.lang.Object)
	 */
	@Override
	protected void editItem(Object itemId) {
		if (itemId == null) {
			LOGGER.warn("Try to EDIT a image with NULL id. RETURN, no action", new IllegalStateException());
			return;
		}

		HomePageImage image = coreService.findById(HomePageImage.class, (Long) itemId);
		if (image != null) {
			UI.getCurrent().addWindow(
					new SlideImagesManagementDialog(image, "Edit image information", new RefreshOnUpdate()));
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
			LOGGER.warn("Try to DELETE a image with NULL itemId. RETURN, no action", new IllegalStateException());
			return;
		}
		HomePageImage image = coreService.findById(HomePageImage.class, (Long) itemId);
		coreService.remove(image);
		refresh();
	}
}
