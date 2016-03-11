package org.tahom.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.UrlTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.UrlValidator;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.User;
import org.tahom.web.mask.MaskIndicatingAjaxButton;

public class ImportTournamentPage extends AbstractBasePage {

	private static final long serialVersionUID = 1L;
	private final User user;
	private final Tournament tournament;

	final FeedbackPanel feedBackPanel;
	final ModalWindow modalWindow;

	public ImportTournamentPage(final ModalWindow modalWindow, final User user, Tournament tournament,
	        FeedbackPanel feedBackPanel) {
		this.user = user;
		this.tournament = tournament;
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

		private boolean ignoreErrors = false;

		private static final long serialVersionUID = 1L;

		private ImportForm() {
			super("importForm");

			TextField<String> url = addUrlField();

			addImportButton(url);
			addCheckBOx();
		}

		private TextField<String> addUrlField() {
			final TextField<String> url = new UrlTextField("url", new Model<String>(), new UrlValidator());
			url.setOutputMarkupId(true);
			url.setRequired(true);

			add(url);
			return url;
		}

		private void addCheckBOx() {
			add(new CheckBox("ignoreErrors", new PropertyModel<Boolean>(this, "ignoreErrors")));
		}

		public void addImportButton(final TextField<String> url) {
			add(new MaskIndicatingAjaxButton("showModalLinkTournament") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void submit(AjaxRequestTarget target, Form<?> form) {
					target.add(feedBackPanel);
					importService.importTournament(url.getInput(), tournament, user, ignoreErrors);
					modalWindow.close(target);
				}

			});
		}
	}
}