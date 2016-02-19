package org.tahom.web.model;

import org.apache.wicket.model.AbstractReadOnlyModel;

public class EvenOddReplaceModel extends AbstractReadOnlyModel<String> {

	private static final long serialVersionUID = 1L;

	private int index;

	public EvenOddReplaceModel(int index) {
		this.index = index;
	}

	@Override
	public String getObject() {
		return (index % 2 == 1) ? "even" : "odd";
	}

}
