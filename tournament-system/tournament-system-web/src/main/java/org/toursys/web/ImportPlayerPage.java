package org.toursys.web;

import java.util.List;

import org.apache.wicket.PageReference;
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
import org.toursys.processor.pdf.PlayersHtmlImportFactory;
import org.toursys.processor.service.PlayerService;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.User;

public class ImportPlayerPage extends WebPage {

    private static final long serialVersionUID = 1L;
    private final User user;

    @SpringBean(name = "playerService")
    protected PlayerService playerService;
    final FeedbackPanel feedBackPanel;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ImportPlayerPage(final PageReference modalWindowPage, final ModalWindow modalWindow, final User user) {
        this.user = user;

        modalWindow.setTitle(getString("setUrl"));
        modalWindow.setResizable(false);

        feedBackPanel = new FeedbackPanel("feedbackPanel");
        add(feedBackPanel.setOutputMarkupId(true));
        add(new ImportForm(modalWindow));
    }

    private class ImportForm extends Form<Void> {

        private static final long serialVersionUID = 1L;

        private ImportForm(final ModalWindow modalWindow) {
            super("importForm");

            final TextField<String> url = new UrlTextField("url", new Model<String>(), new UrlValidator());
            url.setOutputMarkupId(true);

            add(url);

            add(new AjaxButton("cancel") {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    modalWindow.close(target);
                }
            });

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
                        logger.error("Chyba pri importovani hracov: ", e);
                        error(getString("importError"));
                    }
                }
            });
        }
    }
}