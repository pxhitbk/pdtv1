/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui;

import static com.pdt.resources.Messages.get;

import com.pdt.util.Static;
import com.vaadin.ui.Button;

/**
 * @author hungpx
 *
 */
public class CommandButton extends Button {
	/** */
	private static final long serialVersionUID = -328993576404443233L;

	public enum CommandButtonType {
		SAVE, RESET, CLOSE, DELETE, CLONE, UNDEFINE;

		String caption() {
			switch (this) {
			case SAVE:
				return get(Static.CMS_LANGUAGE, "CommandButton.Type_SAVE");
			case RESET:
				return get(Static.CMS_LANGUAGE, "CommandButton.Type_RESET");
			case CLOSE:
				return get(Static.CMS_LANGUAGE, "CommandButton.Type_CLOSE");
			case DELETE:
				return get(Static.CMS_LANGUAGE, "CommandButton.Type_DELETE");
			case CLONE:
				return get(Static.CMS_LANGUAGE, "CommandButton.Type_CLONE");
			default:
				return get(Static.CMS_LANGUAGE, "CommandButton.Type_UNDEFINE");
			}
		}
	};

	private final CommandButtonType commandButtonType;

	public CommandButton(CommandButtonType type) {
		super(type.caption());
		this.commandButtonType = type;
	}

	public CommandButtonType getCommandButtonType() {
		return commandButtonType;
	}
}
