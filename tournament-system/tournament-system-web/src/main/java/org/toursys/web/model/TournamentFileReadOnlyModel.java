package org.toursys.web.model;

import java.io.File;

import org.apache.wicket.Component;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.TournamentException;

public abstract class TournamentFileReadOnlyModel extends AbstractReadOnlyModel<File> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = 1L;

    @Override
    public File getObject() {
        try {
            return getTournamentObject();
        } catch (TournamentException ex) {
            logger.error("Error when submiting button " + this, ex);
            getFormComponent().error(getFormComponent().getString(ex.getCode()));
            return null;
        }
    }

    public abstract Component getFormComponent();

    public abstract File getTournamentObject();

}
