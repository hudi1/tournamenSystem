package org.tahom.web.link;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.TournamentException;

public abstract class TournamentLink extends Link<Void> {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;

	public TournamentLink(String id, IModel<Void> model) {
		super(id, model);
	}

	public TournamentLink(String id) {
		super(id);
	}

	@Override
	public void onClick() {
		try {
			click();
		} catch (TournamentException ex) {
			logger.error("Error when click link " + this, ex);
			throw ex;
		}
	}

	public abstract void click();

}
