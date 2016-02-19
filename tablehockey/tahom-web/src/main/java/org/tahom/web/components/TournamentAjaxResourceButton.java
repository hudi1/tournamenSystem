package org.tahom.web.components;

import org.apache.wicket.model.ResourceModel;

public abstract class TournamentAjaxResourceButton extends TournamentAjaxButton {

	private static final long serialVersionUID = 1L;

	public TournamentAjaxResourceButton(String id) {
		super(id, new ResourceModel(id));
	}

}
