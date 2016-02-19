package org.tahom.web.components;

import org.apache.wicket.model.ResourceModel;

public abstract class TournamentBackResourceButton extends TournamentBackButton {

	private static final long serialVersionUID = 1L;

	public TournamentBackResourceButton(String id) {
		super(id, new ResourceModel(id));
	}
}
