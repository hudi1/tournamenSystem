package org.toursys.web;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.toursys.processor.service.TournamentService;
import org.toursys.repository.model.Season;

public abstract class SeasonEditPage extends BasePage {

	private static final long serialVersionUID = 1L;

	private BasePage pageFrom;

	@SpringBean(name = "tournamentService")
	TournamentService tournamentService;

	private class SeasonForm extends Form<Season> {

		private static final long serialVersionUID = 1L;

		public SeasonForm(final Season season) {
			super("seasonEditForm", new CompoundPropertyModel<Season>(season));
			setOutputMarkupId(true);
			add(new TextField<String>("name"));

			add(new Button("submit") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					tournamentService.createSeason(season);
					setResponsePage(pageFrom);
				}

			});

			add(new Button("back") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					setResponsePage(pageFrom);
				}

				@Override
				public void onError() {
					getSession().cleanupFeedbackMessages();
					setResponsePage(pageFrom);
				}
			});
		}

	}

	public SeasonEditPage(BasePage pageFrom, Season season) {
		this.pageFrom = pageFrom;
		add(new SeasonForm(season));
	}

	@Override
	protected IModel<String> newHeadingModel() {
		return Model.of("Examples - Address Editor");
	}

}
