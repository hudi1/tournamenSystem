package org.tahom.web.components;

import org.apache.wicket.model.ResourceModel;

public abstract class TournamentResourceButton extends TournamentButton {

	private static final long serialVersionUID = 1L;

	public TournamentResourceButton(String id) {
		super(id, new ResourceModel(id));
	}
}
