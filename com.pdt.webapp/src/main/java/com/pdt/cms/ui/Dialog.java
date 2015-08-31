/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui;

import static com.pdt.resources.Messages.get;

import org.vaadin.dialogs.ConfirmDialog;

import com.pdt.cms.ui.CommandButton.CommandButtonType;
import com.pdt.core.model.BaseEntity;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.util.Static;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Dialog extends from vaadin {@link Window}, wrap a data model and common action to make interaction between model and
 * database.
 *
 * @param T
 *            view model
 * @author hungpx
 *
 */
public abstract class Dialog<T> extends Window {

	/** */
	private static final long serialVersionUID = -5814064968996244314L;

	protected static final String DEFAULT_WIDTH = "800px";
	protected static final String DEFAULT_HEIGHT = "700px";

	private Class<T> modelType;
	private T model;
	private AbstractLayout content;
	private CommandButtonType[] commandButtons;
	private boolean isModified;

	/**
	 * Construct Dialog that associate with a data model
	 *
	 * @param model
	 */
	@SuppressWarnings("unchecked")
	public Dialog(T model, CommandButtonType[] buttons, String caption) {
		this((Class<T>) model.getClass(), buttons, model, caption, null);
	}

	/**
	 * Construct Dialog that associate with a data model
	 *
	 * @param model
	 * @param parent
	 *            specific {@link ContentSegment} parent
	 */
	@SuppressWarnings("unchecked")
	public Dialog(T model, CommandButtonType[] buttons, String caption, CloseListener closeListener) {
		this((Class<T>) model.getClass(), buttons, model, caption, closeListener);
	}

	public Dialog(Class<T> modelType, CommandButtonType[] buttons, T model, String caption, CloseListener closeListener) {
		super(caption);
		this.modelType = modelType;
		this.commandButtons = buttons;
		this.model = model;

		content = constructLayout();

		if (content == null)
			content = new VerticalLayout();

		setContent(content);
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
		center();

		if (commandButtons != null && commandButtons.length > 0) {
			HorizontalLayout buttonBar = new HorizontalLayout();
			for (CommandButtonType b : commandButtons) {
				buttonBar.addComponent(createCommandButton(b));
			}
			content.addComponent(buttonBar);
		}

		if (closeListener != null) {
			addCloseListener(closeListener);
		}
	}

	/**
	 * Setup the content layout. This layout will be add to the dialog as the main content
	 *
	 * @return layout that contain all dialog content
	 */
	protected abstract AbstractLayout constructLayout();

	public Class<T> getModelType() {
		return modelType;
	}

	public T getModel() {
		return model;
	}

	private CommandButton createCommandButton(CommandButtonType type) {
		final CommandButton button = new CommandButton(type);
		button.addClickListener(new ClickListener() {
			/** */
			private static final long serialVersionUID = 7153161525089829680L;

			@Override
			public void buttonClick(ClickEvent event) {

				switch (button.getCommandButtonType()) {
				case CLONE:
					// TODO: open dialog request save if window content is modified. Create new dialog with clone entity
					// was present from window.
					break;
				case DELETE:
					ConfirmDialog.show(getUI(), get(Static.CMS_LANGUAGE, "Dialog.Confirm_delete_message"),
							new ConfirmDialog.Listener() {

								/** */
								private static final long serialVersionUID = -8985778528447260916L;

								@Override
								public void onClose(ConfirmDialog dialog) {
									delete();
								}
							});
					close();
					break;
				case SAVE:
					if (save())
						close();
					break;
				case CLOSE:
					close();
					break;
				case RESET:

				default:
					break;
				}
			}
		});
		return button;
	}

	/**
	 * Override this method to make delete action when user trigger the delete button. By default, if T is an entity, it
	 * will be deleted from database. This should be override to delete custom model object with it's reference.
	 *
	 * @return true if success
	 */
	public boolean delete() {
		if (BaseEntity.class.isAssignableFrom(getModelType())) {
			ServiceResolver.findService(CoreService.class).remove((BaseEntity) model);
			return true;
		}
		return false;
	}

	/**
	 * This method will be called when user click save button. Override this method to add or update entity
	 *
	 * @return true if success
	 */
	public boolean save() {
		return false;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	public CommandButtonType[] getCommandButtons() {
		return commandButtons;
	}
}
