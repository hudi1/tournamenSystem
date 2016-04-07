package org.tahom.web;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class ErrorPage500 extends BasePage {

	private static final long serialVersionUID = 1L;

	public ErrorPage500() {
		createPage();
	}

	private void createPage() {
		add(new Image("error", new ContextRelativeResource("/img/error-lolcat-problemz.jpg")));
	}

	@Override
	protected IModel<String> newHeadingModel() {
		return null;
	}

}
