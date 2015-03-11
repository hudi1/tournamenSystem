package org.toursys.web;

import java.util.Collections;
import java.util.Comparator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.User;
import org.toursys.web.components.PropertyPageableListView;
import org.toursys.web.link.AjaxModelLink;

@AuthorizeInstantiation(Roles.USER)
public class PlayerPage extends TournamentHomePage {

    private static final long serialVersionUID = 1L;
    private User user;

    public PlayerPage() {
        this(new PageParameters());
    }

    public PlayerPage(PageParameters pageParameters) {
        super(pageParameters);
        this.user = getTournamentSession().getUser();

        createPage();
    }

    protected void createPage() {
        add(new PlayerForm(Model.of(getUserWithPlayers())));
    }

    private User getUserWithPlayers() {
        User user = new User();
        user.getPlayers().addAll(playerService.getPlayers(new Player()._setUser(this.user)));
        Collections.sort(user.getPlayers(), new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.getSurname().compareTo(p2.getSurname());
            }
        });
        return user;
    }

    private class PlayerForm extends Form<User> {

        private static final long serialVersionUID = 1L;

        public PlayerForm(Model<User> model) {
            super("playerForm", new CompoundPropertyModel<User>(model));
            ModalWindow modalWindow;

            add(new Label("name", new ResourceModel("name")));
            add(new Label("surname", new ResourceModel("surname")));
            add(new Label("club", new ResourceModel("club")));

            addPlayerListView();
            addNewPlayerButton();
            add(modalWindow = createModalWindow());
            addModalButton(modalWindow);
        }

        private void addPlayerListView() {
            PropertyPageableListView<Player> listView;
            add(listView = new PropertyPageableListView<Player>("players", ITEM_PER_PAGE) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<Player> listItem) {
                    final Player player = listItem.getModelObject();
                    listItem.setModel(new CompoundPropertyModel<Player>(player));
                    listItem.add(new TextField<String>("name").add(new AjaxFormComponentUpdatingBehavior("onchange") {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void onUpdate(AjaxRequestTarget target) {
                            playerService.updatePlayer(listItem.getModelObject());
                        }
                    }));

                    listItem.add(new TextField<String>("surname")
                            .add(new AjaxFormComponentUpdatingBehavior("onchange") {

                                private static final long serialVersionUID = 1L;

                                @Override
                                protected void onUpdate(AjaxRequestTarget target) {
                                    Player player = listItem.getModelObject();
                                    if (player.getSurname().contains(" ")) {
                                        player.setPlayerDiscriminator(player.getSurname().split(" ")[1].substring(0,
                                                Math.min(player.getSurname().split(" ")[1].length(), 3)));
                                        player.setSurname(player.getSurname().split(" ")[0]);
                                    }
                                    playerService.updatePlayer(player);
                                }
                            }));

                    listItem.add(new TextField<String>("club").add(new AjaxFormComponentUpdatingBehavior("onchange") {

                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void onUpdate(AjaxRequestTarget target) {
                            playerService.updatePlayer(listItem.getModelObject());
                        }
                    }));

                    listItem.add(new AjaxLink<Void>("deletePlayer") {

                        private static final long serialVersionUID = 1L;

                        public void onClick(AjaxRequestTarget target) {
                            PlayerForm.this.getModelObject().getPlayers().remove(listItem.getModelObject());
                            playerService.deletePlayer(listItem.getModelObject());
                            target.add(PlayerForm.this);
                        }

                        @Override
                        protected IAjaxCallDecorator getAjaxCallDecorator() {
                            return new AjaxCallDecorator() {

                                private static final long serialVersionUID = 1L;

                                @Override
                                public CharSequence decorateScript(Component c, CharSequence script) {
                                    return "if(confirm(" + getString("del.player") + ")){ " + script
                                            + "}else{return false;}";
                                }

                            };
                        }
                    }.add(new Image("img", new SharedResourceReference("delete"))).add(
                            AttributeModifier.replace("title", new AbstractReadOnlyModel<String>() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                public String getObject() {
                                    return getString("deletePlayer");
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

        private void addModalButton(final ModalWindow modalWindow) {
            add(new AjaxModelLink<Void>("showModalLink") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onClick(AjaxRequestTarget target) {
                    modalWindow.show(target);
                }
            });
        }

        private void addNewPlayerButton() {
            add(new Button("newPlayer", new ResourceModel("newPlayer")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(PlayerEditPage.class, getPageParameters());
                }
            });
        }

        private ModalWindow createModalWindow() {
            final ModalWindow modal;
            add(modal = new ModalWindow("modal"));
            modal.setCookieName("modal-1");

            modal.setPageCreator(new ModalWindow.PageCreator() {

                private static final long serialVersionUID = 1L;

                public Page createPage() {
                    return new ImportPlayerPage(modal, user);
                }
            });

            modal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

                private static final long serialVersionUID = 1L;

                public boolean onCloseButtonClicked(AjaxRequestTarget target) {
                    return true;
                }
            });

            modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

                private static final long serialVersionUID = 10094L;

                public void onClose(AjaxRequestTarget target) {
                    setResponsePage(PlayerPage.class, getPageParameters());
                }
            });
            return modal;
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("playerList");
    }
}
