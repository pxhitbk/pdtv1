package com.pdt.cms.ui.perspective;

import static com.pdt.resources.Messages.get;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.pdt.cms.ui.ContentSegment;
import com.pdt.cms.ui.model.GroupModel;
import com.pdt.cms.ui.model.PerspectiveModel;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * <p>
 * Common setting for perspective.
 * </p>
 * |___________contenContainer___________|<br/>
 * |side||_______________________________|<br/>
 * |bar~||[title]~~~~~~~~~~~~~~~~~~~~~~~~|<br/>
 * |~~~~||_______________________________|<br/>
 * |~~~~||contentSegment~~~~~~~~~~~~~~~~~|<br/>
 * |____||_______________________________|<br/>
 *
 * @author hungpx
 *
 */
public class AbstractPerspective implements Perspective {

	public static final String DEFAULT_SIDEBAR_WIDTH = get("AbstractPerspective.defaulSidebarWidth");

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLayout.class);
	private boolean isActive;
	private PerspectiveModel perspectiveModel;
	private AbstractLayout container;
	private List<ContentSegment> contentSegments;
	/** Tab sheet contains more than one content segment */
	private TabSheet contentSegmentTabSheet;

	public AbstractPerspective(PerspectiveModel perspectiveModel) {
		this.perspectiveModel = perspectiveModel;
	}

	@Override
	public void init() {

		List<String> contentTypeNames = perspectiveModel.getContentClasses();
		contentSegments = new ArrayList<ContentSegment>();
		for (String contentTypeName : contentTypeNames) {
			try {
				Class<?> contentType = Class.forName(contentTypeName);
				if (!ContentSegment.class.isAssignableFrom(contentType))
					throw new IllegalArgumentException(contentType.getName() + " is invalid content segment type.");

				ContentSegment contentSegement = (ContentSegment) contentType.newInstance();
				contentSegement.init();
				contentSegments.add(contentSegement);
				Collections.sort(contentSegments, new Comparator<ContentSegment>() {

					@Override
					public int compare(ContentSegment o1, ContentSegment o2) {
						return o2.getOrder() - o1.getOrder();
					}
				});
			} catch (ClassNotFoundException e) {
				LOGGER.error("Cannot load content layout", e);
			} catch (InstantiationException | IllegalAccessException e) {
				LOGGER.error("Cannot initialize content segment for " + perspectiveModel.getTitle(), e);
			}
		}

		if (getSidebar() != null) {
			container = new HorizontalLayout();
			container.addComponent(getSidebar());
		} else {
			container = new VerticalLayout();
		}

		if (perspectiveModel.getCaption() != null) {
			Label captionLbl = new Label(perspectiveModel.getCaption());
			captionLbl.setStyleName("abstractperspective-caption");
			container.addComponent(captionLbl);
		}

		if (contentSegments.size() == 1) {
			container.addComponent(contentSegments.get(0).getLayout());
		} else if (contentSegments.size() > 1) {
			contentSegmentTabSheet = new TabSheet();
			for (int i = 0; i < contentSegments.size(); i++) {
				final ContentSegment segment = contentSegments.get(i);
				AbstractLayout tab = segment.getLayout();
				contentSegmentTabSheet.addTab(tab, segment.getName());
				container.addComponent(contentSegmentTabSheet);
				contentSegmentTabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {

					/** */
					private static final long serialVersionUID = 1L;

					@Override
					public void selectedTabChange(SelectedTabChangeEvent event) {
						ContentSegment old = getActiveContentSegment();
						if (old != null && old.getName() != segment.getName())
							old.deactive();
						segment.activate();
					}
				});
			}
		}
	}

	@Override
	public void activate() {
		isActive = true;

		if (contentSegmentTabSheet != null)
			contentSegmentTabSheet.setSelectedTab(0);
		else
			contentSegments.get(0).activate();
	}

	public PerspectiveModel getPerspectiveModel() {
		return perspectiveModel;
	}

	public void setPerspectiveModel(PerspectiveModel perspectiveModel) {
		this.perspectiveModel = perspectiveModel;
	}

	/**
	 * Get current active content. If there's not content segment was activated, then first segment will be return
	 *
	 * @return active content segment component
	 */
	protected AbstractLayout getActiveContent() {
		if (contentSegments.size() > 1)
			return (AbstractLayout) contentSegmentTabSheet.getSelectedTab();
		return contentSegments.get(0).getLayout();
	}

	protected ContentSegment getActiveContentSegment() {
		List<ContentSegment> iterables = FluentIterable.from(contentSegments).filter(new Predicate<ContentSegment>() {

			@Override
			public boolean apply(ContentSegment input) {
				return input.isActive() == true;
			}
		}).toList();
		if (iterables.size() > 1) {
			LOGGER.debug("There're " + iterables.size() + " active content segment in perspective: "
					+ getModel().getPath(), new IllegalStateException());
			return null;
		}
		if (iterables.size() == 0)
			return null;
		return iterables.get(0);
	}

	/**
	 * Define the sidebar that will be laid in the left of the perspective.
	 */
	@Override
	public VerticalLayout getSidebar() {
		return null;
	}

	/**
	 * Main component that contain sidebar (if exist) and content segment
	 *
	 * @return the container
	 */
	@Override
	public AbstractComponentContainer getContainer() {
		return container;
	}

	@Override
	public GroupModel getGroup() {
		return perspectiveModel.getGroup();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.perspective.Perspective#isActive()
	 */
	@Override
	public boolean isActive() {
		return isActive;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.perspective.Perspective#deactivate()
	 */
	@Override
	public void deactivate() {
		isActive = false;
		for (ContentSegment segment : contentSegments)
			segment.deactive();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.perspective.Perspective#getModel()
	 */
	@Override
	public PerspectiveModel getModel() {
		return perspectiveModel;
	}
}
