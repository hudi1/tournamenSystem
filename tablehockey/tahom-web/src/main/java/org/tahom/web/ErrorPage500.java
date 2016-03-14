package org.tahom.web;

import org.apache.wicket.model.IModel;

public class ErrorPage500 extends BasePage {

	private static final long serialVersionUID = 1L;

	public ErrorPage500() {
		createPage();
	}

	private void createPage() {
	}

	@Override
	protected IModel<String> newHeadingModel() {
		return null;
	}

}
