package org.tahom.web;

import org.apache.wicket.model.IModel;
import org.tahom.web.components.ResourceLabel;

public class ErrorPage404 extends BasePage {

	private static final long serialVersionUID = 1L;

	public ErrorPage404() {
		createPage();
	}

	private void createPage() {
		add(new ResourceLabel("error404"));
	}

	@Override
	protected IModel<String> newHeadingModel() {
		return null;
	}

}
