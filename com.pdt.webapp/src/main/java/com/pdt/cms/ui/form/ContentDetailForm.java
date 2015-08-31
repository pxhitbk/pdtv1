/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.form;

import com.pdt.cms.ui.util.FieldFactory;
import com.pdt.core.model.Articles;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;

/**
 * Form for update article
 * @author august
 *
 */
public class ContentDetailForm extends FormLayout {
	/** */
	private static final long serialVersionUID = 4050007804959057704L;

	private BeanFieldGroup<Articles> articlesBinder;
	private final Articles content;

	private boolean modified;

	/**
	 * @param content
	 */
	public ContentDetailForm(Articles content) {
		this.content = content;
		init();
	}

	/**
	 *
	 */
	private void init() {
		BeanItem<Articles> articlesItem = new BeanItem<Articles>(content);
		articlesBinder = new BeanFieldGroup<Articles>(Articles.class);
		articlesBinder.setItemDataSource(articlesItem);

		TextField title = FieldFactory.createTextField(TextField.class, "Tiêu đề", null, true, null);
		RichTextArea basicContent = new RichTextArea("Lead", articlesItem.getItemProperty("lead"));
		RichTextArea articleContent = new RichTextArea("Detail", articlesItem.getItemProperty("content"));

		articleContent.setImmediate(true);
		addComponent(title);
		addComponent(basicContent);
		addComponent(articleContent);

		// cityLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
		articlesBinder.bind(title, "title");
		articlesBinder.bind(basicContent, "lead");
		articlesBinder.bind(articleContent, "content");

		articlesBinder.bindMemberFields(this);
	}

	public boolean commit() {
		try {
			setModified(articlesBinder.isModified());
			articlesBinder.commit();
		} catch (CommitException e) {
			for (Component c : this)
				if (c instanceof AbstractField<?>) {
					try {
						((AbstractField<?>) c).validate();
					} catch (InvalidValueException ex) {
						((AbstractField<?>) c).setComponentError(new UserError(ex.getMessage()));
					}
				}
			return false;
		}
		return true;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public Articles getModel() {
		return content;
	}
}
