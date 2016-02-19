package org.tahom.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.UrlTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.UrlValidator;
import org.tahom.repository.model.User;
import org.tahom.web.mask.MaskIndicatingAjaxButton;

public class ImportPlayerPage extends AbstractBasePage {

	private static final long serialVersionUID = 1L;
	private final User user;

	private final FeedbackPanel feedBackPanel;
	private final ModalWindow modalWindow;

	public ImportPlayerPage(final ModalWindow modalWindow, final User user, FeedbackPanel feedBackPanel) {
		this.user = user;
		this.feedBackPanel = feedBackPanel;
		this.modalWindow = modalWindow;
		createPage();
	}

	private void createPage() {
		modalWindow.setTitle(getString("setUrl"));
		modalWindow.setResizable(false);

		add(feedBackPanel.setOutputMarkupId(true));
		add(new ImportForm());
	}

	private class ImportForm extends Form<Void> {

		private static final long serialVersionUID = 1L;

		private ImportForm() {
			super("importForm");

			TextField<String> url = addUrlField();
			addImportButton(url);
		}

		private TextField<String> addUrlField() {
			final TextField<String> url = new UrlTextField("url", new Model<String>(), new UrlValidator());
			url.setOutputMarkupId(true);
			url.setRequired(true);

			add(url);
			return url;
		}

		public void addImportButton(final TextField<String> url) {
			add(new MaskIndicatingAjaxButton("import") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void submit(AjaxRequestTarget target, Form<?> form) {
					target.add(feedBackPanel);
					importService.importPlayers(url.getInput(), user);
					modalWindow.close(target);
				}

				@Override
				public String maskText() {
					return getString("maskText");
				}
			});
		}
	}
}