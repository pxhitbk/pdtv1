package com.pdt.cms.ui;

import java.util.Map;

import com.pdt.cms.ui.perspective.Perspective;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * <p>
 * Main UI for Content Management System
 * </p>
 *
 * @author hungpx
 * @since
 */
@Theme("pdt")
public class CmsUI extends UI {

	private static final long serialVersionUID = -8040348056976003238L;

	private static final CmsApplicationUiContext CONTEXT = new CmsApplicationUiContext();
	/** Layout contains all the website content */
	private VerticalLayout mainLayout;
	private CssLayout header;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout systemWide = new VerticalLayout();
		// systemWide.setSizeFull();
		systemWide.setStyleName("cmsui-systemwide");
		setContent(systemWide);

		mainLayout = new VerticalLayout();
		mainLayout.setStyleName("cmsui-webcontainer");
		systemWide.addComponent(mainLayout);

		header = new CssLayout();
		header.setStyleName("cmsui-header");
		mainLayout.addComponent(header);

		initPerspectives();
	}

	private void initPerspectives() {
		MenuBar menuBar = new MenuBar();
		header.addComponent(menuBar);

		int count = 1;
		for (String gid : PerspectiveDefinition.DECLARED_GROUPS.keySet()) {
			Map<String, Perspective> pg = PerspectiveDefinition.getPerspectivesByGroup(gid);
			if (pg.size() == 1) {
				Perspective p = PerspectiveDefinition.getPerspectiveContainer(pg.keySet().iterator().next());
				p.init();
				setupUiForPerspectiveContainer(p.getContainer());
				menuBar.addItem(p.getModel().getCaption(), null, new ActivePerspectiveCommand(p));
				if (count == 1) {
					mainLayout.addComponent(p.getContainer());
					CONTEXT.setActivePerspective(p);
					p.activate();
				}
				count++;
			} else {
				MenuItem groupItem = menuBar.addItem(PerspectiveDefinition //
						.getGroupModelById(gid).getCaption(), null, null);
				for (String path : PerspectiveDefinition.DECLARED_PERSPECTIVES.keySet()) {
					Perspective p = PerspectiveDefinition.getPerspectiveContainer(path);
					if (p.getGroup().getId().equals(gid)) {
						p.init();
						setupUiForPerspectiveContainer(p.getContainer());
						groupItem.addItem(p.getModel().getCaption(), null, new ActivePerspectiveCommand(p));
						if (count == 1) {
							p.activate();
							CONTEXT.setActivePerspective(p);
						}
						count++;
					}
				}
			}
		}
	}

	private void setupUiForPerspectiveContainer(AbstractComponentContainer container) {
		container.setImmediate(true);
		container.setStyleName("cms-perspectivecontainer");
	}

	/**
	 * Get the UI context of CMS application
	 *
	 * @return
	 */
	public static CmsApplicationUiContext getContext() {
		return CONTEXT;
	}

	class ActivePerspectiveCommand implements Command {

		/** */
		private static final long serialVersionUID = 5428326999981463907L;
		private Perspective perspective;

		ActivePerspectiveCommand(Perspective perspective) {
			this.perspective = perspective;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.vaadin.ui.MenuBar.Command#menuSelected(com.vaadin.ui.MenuBar.MenuItem)
		 */
		@Override
		public void menuSelected(MenuItem selectedItem) {
			if (perspective.getModel().getPath().equals(Page.getCurrent()))
				return;
			if (CONTEXT.getActivePerspective() != null) {
				mainLayout.replaceComponent(CONTEXT.getActivePerspective().getContainer(), perspective.getContainer());
				CONTEXT.getActivePerspective().deactivate();
			} else
				mainLayout.addComponent(perspective.getContainer());
			CONTEXT.setActivePerspective(perspective);
			perspective.activate();
		}
	}
}
