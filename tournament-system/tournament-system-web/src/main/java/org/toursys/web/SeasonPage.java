package org.toursys.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.User;

@AuthorizeInstantiation(Roles.USER)
public class SeasonPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private User user;

    public SeasonPage() {
        this.user = getTournamentSession().getUser();
        createPage();
    }

    protected void createPage() {
        add(new SeasonForm(Model.of(user)));
    }

    private class SeasonForm extends Form<User> {

        private static final long serialVersionUID = 1L;

        public SeasonForm(IModel<User> model) {
            super("seasonForm", new CompoundPropertyModel<User>(model));
            addSeasonListView();
            addSeasonAddButton();
        }

        private void addSeasonListView() {
            PageableListView<Season> listView;
            add(listView = new PageableListView<Season>("seasons", getModelObject().getSeasons(), ITEM_PER_PAGE) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<Season> listItem) {
                    listItem.setModel(new CompoundPropertyModel<Season>(listItem.getModel()));
                    listItem.add(new AjaxEditableLabel<String>("name") {

                        private static final long serialVersionUID = 1L;

                        public void onSubmit(AjaxRequestTarget target) {
                            super.onSubmit(target);
                            seasonService.updateSeason(listItem.getModelObject());
                        };

                    });
                    listItem.add(new AjaxLink<Void>("deleteSeason") {

                        private static final long serialVersionUID = 1L;

                        public void onClick(AjaxRequestTarget target) {
                            SeasonForm.this.getModelObject().getSeasons().remove(listItem.getModelObject());
                            target.add(SeasonForm.this);
                            seasonService.deleteSeason(listItem.getModelObject());
                        }

                        @Override
                        protected IAjaxCallDecorator getAjaxCallDecorator() {
                            return new AjaxCallDecorator() {

                                private static final long serialVersionUID = 1L;

                                @Override
                                public CharSequence decorateScript(Component c, CharSequence script) {
                                    return "if(confirm(" + getString("del.season") + ")){ " + script
                                            + "}else{return false;}";
                                }

                            };
                        }
                    }.add(AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return getString("deleteSeason");
                        }
                    })));

                    listItem.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public String getObject() {
                            return (listItem.getIndex() % 2 == 1) ? "even" : "odd";
                        }
                    }));
                }

            });
            PagingNavigator navigator = new PagingNavigator("navigator", listView);
            add(navigator);
        }

        private void addSeasonAddButton() {
            add(new AjaxButton("addSeason", new ResourceModel("addSeason")) {

                private static final long serialVersionUID = 1L;

                public void onClick(AjaxRequestTarget target) {
                    Season season = new Season();
                    season.setUser(user);
                    season.setName(getString("enterName"));
                    user.getSeasons().add(seasonService.createSeason(season));
                    target.add(SeasonForm.this);
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    onClick(target);
                }

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    onClick(target);
                }

            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("selectSeason");
    }
}
