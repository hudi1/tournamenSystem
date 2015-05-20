package org.toursys.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
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
import org.toursys.processor.service.ImportService;
import org.toursys.repository.model.User;
import org.toursys.web.mask.MaskIndicatingAjaxButton;

public class ImportPlayerPage extends WebPage {

    private static final long serialVersionUID = 1L;
    private final User user;

    @SpringBean(name = "importService")
    private ImportService importService;

    private final FeedbackPanel feedBackPanel;
    private final ModalWindow modalWindow;

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
                    // TODO test if it is nesessary target.add(feedBackPanel);
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