package org.tahom.web.model;

import org.apache.wicket.model.AbstractReadOnlyModel;

public class ItalicBoldReplaceModel extends AbstractReadOnlyModel<String> {

	private static final long serialVersionUID = 1L;

	private boolean add;

	public ItalicBoldReplaceModel(boolean add) {
		this.add = add;
	}

	@Override
	public String getObject() {
		if (add) {
			StringBuilder text = new StringBuilder();
			text.append("font-style: italic;");
			text.append("font-weight:bold;");
			return text.toString();
		}
		return "";
	}

}
