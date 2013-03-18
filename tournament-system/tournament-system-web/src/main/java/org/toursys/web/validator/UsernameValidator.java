package org.toursys.web.validator;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.service.TournamentService;
import org.toursys.repository.model.User;

public class UsernameValidator extends AbstractValidator<String> {

    @SpringBean(name = "tournamentService")
    protected TournamentService tournamentService;

    private static final long serialVersionUID = 1L;

    private static final UsernameValidator INSTANCE = new UsernameValidator();

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static UsernameValidator getInstance() {
        return INSTANCE;
    }

    @Override
    protected void onValidate(IValidatable<String> validatable) {
        String username = validatable.getValue();
        User user = tournamentService.getUser(new User()._setUserName(username));
        if (user != null) {
            error(validatable);
        }

    }

    @Override
    protected String resourceKey() {
        return "error.username";
    }

}
