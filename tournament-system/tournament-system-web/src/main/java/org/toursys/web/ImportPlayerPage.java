package org.toursys.web;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.UrlTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.html.PlayersHtmlImportFactory;
import org.toursys.processor.service.PlayerService;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.User;

public class ImportPlayerPage extends WebPage {

    private static final long serialVersionUID = 1L;
    private final User user;

    @SpringBean(name = "playerService")
    protected PlayerService playerService;
    final FeedbackPanel feedBackPanel;
    final ModalWindow modalWindow;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ImportPlayerPage(final ModalWindow modalWindow, final User user) {
        this.user = user;
        this.feedBackPanel = new FeedbackPanel("feedbackPanel");
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
            addCancelButton();
            addImportButton(url);
        }

        private void addCancelButton() {
            add(new AjaxButton("cancel") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    modalWindow.close(target);
                }
            });
        }

        private TextField<String> addUrlField() {
            final TextField<String> url = new UrlTextField("url", new Model<String>(), new UrlValidator());
            url.setOutputMarkupId(true);
            url.setRequired(true);

            add(url);
            return url;
        }

        public void addImportButton(final TextField<String> url) {
            add(new AjaxButton("import") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    target.add(feedBackPanel);
                    try {
                        List<Player> players = PlayersHtmlImportFactory.createdImportedPlayers(url.getInput(), user);
                        for (Player player : players) {
                            playerService.createPlayer(player);
                        }
                        modalWindow.close(target);
                    } catch (Exception e) {
                        logger.error("Error during importing player: ", e);
                        error(getString("importError"));
                    }
                }
            });
        }
    }

}