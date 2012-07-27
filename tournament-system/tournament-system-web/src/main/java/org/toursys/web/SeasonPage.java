package org.toursys.web;

import java.util.Iterator;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.toursys.processor.service.TournamentService;
import org.toursys.repository.model.Season;

public class SeasonPage extends BasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "tournamentService")
	TournamentService tournamentService;

	public SeasonPage() {
		super();
		createPage();
	}

	private void createPage() {
		// create a Data Provider
		IDataProvider<Season> seasonDataProvider = new IDataProvider<Season>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<Season> iterator(int first, int count) {
				return tournamentService.getAllSeason().iterator();
			}

			@Override
			public int size() {
				return tournamentService.getAllSeason().size();
			}

			@Override
			public IModel<Season> model(final Season object) {
				return new LoadableDetachableModel<Season>() {

					private static final long serialVersionUID = 1L;

					@Override
					protected Season load() {
						return object;
					}
				};
			}

			@Override
			public void detach() {
			}
		};

		DataView<Season> dataView = new DataView<Season>("rows",
				seasonDataProvider, 10) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Season> listItem) {
				final Season season = listItem.getModelObject();
				listItem.setModel(new CompoundPropertyModel<Season>(season));
				listItem.add(link("details", season));
				/*listItem.add(new Button("editSeason") {

					private static final long serialVersionUID = 1L;

					private void edit() {
						setResponsePage(new SeasonEditPage(SeasonPage.this,
								((Season) listItem.getDefaultModelObject())
										.clone()) {

							private static final long serialVersionUID = 1L;

						});
					}

					@Override
					public void onError() {
						edit();
					}

					@Override
					public void onSubmit() {
						edit();
					}

				});*/
			}
		};

		add(dataView);

		Form form = new Form<Void>("seasonForm");
		add(form);
		form.add(new Button("newSeason") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				setResponsePage(new SeasonEditPage(SeasonPage.this,
						new Season()) {

					private static final long serialVersionUID = 1L;
				});
			}

		});

	}

	@Override
	protected IModel<String> newHeadingModel() {
		return Model.of("Home Page");
	}

	public static BookmarkablePageLink<Void> link(final String name,
			final Season season) {

		final BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(
				name, PublicPage.class);

		if (season != null) {
			link.getPageParameters().set("itemid", season.getSeasonId());
			link.add(new Label("name", new Model<Season>(season)));
		}

		return link;
	}

}
