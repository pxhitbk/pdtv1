/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.dialog;

import com.pdt.cms.ui.CommandButton.CommandButtonType;
import com.pdt.cms.ui.Dialog;
import com.pdt.cms.ui.form.ContentDetailForm;
import com.pdt.core.model.Articles;
import com.pdt.core.model.StaticContent;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public class ContentManagementDialog extends Dialog<StaticContent> {

	/** */
	private static final long serialVersionUID = 1081518812579612732L;

	private CoreService coreService = ServiceResolver.findService(CoreService.class);

	private static final CommandButtonType[] BUTTONS = new CommandButtonType[] { CommandButtonType.SAVE,
			CommandButtonType.CLOSE };

	private ContentDetailForm contentDetailForm;

	/**
	 * @param hotel
	 * @param string
	 * @param refreshOnUpdate
	 */
	public ContentManagementDialog(StaticContent model, String caption, CloseListener closeListener) {
		super(model, BUTTONS, caption, closeListener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#constructLayout()
	 */
	@Override
	protected AbstractLayout constructLayout() {
		VerticalLayout layout = new VerticalLayout();
		Articles articles = null;
		if (getModel().getArticlesId() == null)
			articles = new Articles();
		else
			articles = ServiceResolver.findService(CoreService.class).findById(Articles.class,
					getModel().getArticlesId());

		contentDetailForm = new ContentDetailForm(articles);

		layout.addComponent(contentDetailForm);

		return layout;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#save()
	 */
	@Override
	public boolean save() {
		boolean commitSuccess = contentDetailForm.commit();
		if (!contentDetailForm.isModified() && commitSuccess)
			return true;
		if (commitSuccess) {
			StaticContent content = getModel();
			Articles a = contentDetailForm.getModel();
			content.setArticles(a);
			content.setName(a.getTitle());
			coreService.insertOrUpdate(content);
			return true;
		}
		return false;
	}
}
