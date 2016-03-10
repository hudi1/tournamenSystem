package org.tahom.web.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;

public class CloseOnESCBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;
	private final ModalWindow modal;

	public CloseOnESCBehavior(ModalWindow modal) {
		this.modal = modal;
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		modal.close(target);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		response.render(JavaScriptReferenceHeaderItem.forScript("" + "$(document).ready(function() {\n"
		        + "  $(document).bind('keyup', function(evt) {\n" + "    if (evt.keyCode == 27) {\n"
		        + getCallbackScript() + "\n" + "        evt.preventDefault();\n" + "    }\n" + "  });\n" + "});",
		        "closeModal", null));
	}
}