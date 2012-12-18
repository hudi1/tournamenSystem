package org.toursys.web;

import java.util.Stack;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TournamentSession extends WebSession {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = 1L;
    private Stack<BasePage> previosPage = new Stack<BasePage>();

    public TournamentSession(Request request) {
        super(request);
    }

    public void addPreviosPage(BasePage basePage) {
        logger.info(basePage.getClass().toString());
        previosPage.push(basePage);
    }

    public BasePage getPreviousPage() {
        BasePage basePage = previosPage.pop();
        logger.info(basePage.getClass().toString());

        return basePage;
    }
}
