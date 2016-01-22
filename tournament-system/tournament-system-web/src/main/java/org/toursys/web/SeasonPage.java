package org.toursys.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.User;
import org.toursys.web.components.PropertyPageableListView;
import org.toursys.web.components.TournamentAjaxButton;
import org.toursys.web.components.TournamentAjaxEditableLabel;
import org.toursys.web.link.TournamentAjaxLink;

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
            PropertyPageableListView<Season> listView;
            add(listView = new PropertyPageableListView<Season>("seasons", ITEM_PER_PAGE) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<Season> listItem) {
                    listItem.add(new TournamentAjaxEditableLabel("name") {

                        private static final long serialVersionUID = 1L;

                        public void submit(AjaxRequestTarget target) {
                            seasonService.updateSeason(listItem.getModelObject());
                        };

                    });
                    listItem.add(new TournamentAjaxLink("deleteSeason") {

                        private static final long serialVersionUID = 1L;

                        public void click(AjaxRequestTarget target) {
                            Season season = listItem.getModelObject();
                            SeasonForm.this.getModelObject().getSeasons().remove(season);
                            seasonService.deleteSeason(season);
                            target.add(SeasonForm.this);
                        }

                        @Override
                        protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                            super.updateAjaxAttributes(attributes);
                            attributes.getAjaxCallListeners().add(new AjaxCallListener() {

                                private static final long serialVersionUID = 1L;

                                @Override
                                public CharSequence getSuccessHandler(Component component) {
                                    return "if(!confirm('" + getString("del.season") + "')) return false;";
                                }

                            });
                        }

                    }.add(new Image("img", new SharedResourceReference("delete"))).add(
                            AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
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
            AjaxPagingNavigator navigator = new AjaxPagingNavigator("navigator", listView);
            add(navigator);
        }

        private void addSeasonAddButton() {
            add(new TournamentAjaxButton("addSeason", new ResourceModel("addSeason")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void submit(AjaxRequestTarget target, Form<?> form) {
                    Season season = new Season();
                    season.setUser(user);
                    season.setName(getString("enterName"));
                    user.getSeasons().add(seasonService.createSeason(season));
                    target.add(SeasonForm.this);
                }

            });
        }
    }

}
