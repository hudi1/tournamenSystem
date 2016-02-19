package org.tahom.web.link;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.TournamentException;

public abstract class TournamentAjaxLink extends AjaxLink<Void> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = 1L;

	private String confirmMessage;

	public TournamentAjaxLink(String id, IModel<Void> model, String confirmMessage) {
		super(id, model);
		this.confirmMessage = confirmMessage;
	}

	public TournamentAjaxLink(String id, IModel<Void> model) {
		this(id, model, null);
	}

	public TournamentAjaxLink(String id, String confirmMessage) {
		this(id, null, confirmMessage);
	}

	public TournamentAjaxLink(String id) {
		this(id, null, null);
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		try {
			click(target);
		} catch (TournamentException ex) {
			logger.error("Error when click link " + this, ex);
			throw ex;
		}
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
		super.updateAjaxAttributes(attributes);
		if (confirmMessage != null) {
			AjaxCallListener ajaxCallListener = new AjaxCallListener();
			ajaxCallListener.onPrecondition("return confirm('" + confirmMessage + "');");
			attributes.getAjaxCallListeners().add(ajaxCallListener);
		}
	}

	public abstract void click(AjaxRequestTarget target);

}
