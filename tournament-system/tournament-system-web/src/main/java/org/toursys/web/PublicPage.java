package org.toursys.web;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class PublicPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public PublicPage() {
		super();
		add(new BookmarkablePageLink<Void>("homePage", HomePage.class));
	}

	@Override
	protected IModel<String> newHeadingModel() {
		return Model.of("Examples - Public Page");
	}

}
