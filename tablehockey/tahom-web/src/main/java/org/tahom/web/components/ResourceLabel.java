package org.tahom.web.components;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;

public class ResourceLabel extends Label {

	private static final long serialVersionUID = 1L;
	private static final String LABEL = "Label";

	public ResourceLabel(String id) {
		super(id, new ResourceModel(id.replace(LABEL, "")));
	}

}
