package org.tahom.web;

import org.apache.wicket.Component;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authentication.strategy.DefaultAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.tahom.repository.model.User;
import org.tahom.repository.model.UserRole;
import org.tahom.web.components.ResourceLabel;
import org.tahom.web.components.TournamentResourceButton;

/**
 * Reusable user sign in panel with username and password as well as support for persistence of the both. When the
 * SignInPanel's form is submitted, the method signIn(String, String) is called, passing the username and password
 * submitted. The signIn() method should authenticate the user's session.
 * 
 * @see {@link IAuthenticationStrategy}
 * @see {@link ISecuritySettings#getAuthenticationStrategy()}
 * @see {@link DefaultAuthenticationStrategy}
 * @see {@link WebSession#authenticate(String, String)}
 * 
 * @author Jonathan Locke
 * @author Juergen Donnerstag
 * @author Eelco Hillenius
 * @author Tomas Hudec
 */
public class TournamentSignInPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private static final String SIGN_IN_FORM = "signInForm";

	/** True if the panel should display a remember-me checkbox */
	private boolean includeRememberMe = true;

	/** True if the user should be remembered via form persistence (cookies) */
	private boolean rememberMe = true;

	/** password. */
	private String password;

	/** user name. */
	private String username;

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public TournamentSignInPanel(final String id) {
		this(id, true);
	}

	/**
	 * @param id
	 *            See Component constructor
	 * @param includeRememberMe
	 *            True if form should include a remember-me checkbox
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public TournamentSignInPanel(final String id, final boolean includeRememberMe) {
		super(id);

		this.includeRememberMe = includeRememberMe;
		add(new SignInForm());
	}

	/**
	 * 
	 * @return signin form
	 */
	protected SignInForm getForm() {
		return (SignInForm) get(SIGN_IN_FORM);
	}

	/**
	 * Convenience method to access the password.
	 * 
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the password
	 * 
	 * @param password
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Convenience method to access the username.
	 * 
	 * @return The user name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username
	 * 
	 * @param username
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Get model object of the rememberMe checkbox
	 * 
	 * @return True if user should be remembered in the future
	 */
	public boolean getRememberMe() {
		return rememberMe;
	}

	/**
	 * @param rememberMe
	 *            If true, rememberMe will be enabled (username and password will be persisted somewhere)
	 */
	public void setRememberMe(final boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	/**
	 * Sign in user if possible.
	 * 
	 * @param username
	 *            The username
	 * @param password
	 *            The password
	 * @return True if signin was successful
	 */
	private boolean signIn(String username, String password) {
		return AuthenticatedWebSession.get().signIn(username, password);
	}

	/**
	 * Called when sign in failed
	 */
	protected void onSignInFailed() {
		error(getString("signInFailed"));
	}

	/**
	 * Called when sign in was successful
	 */
	protected void onSignInSucceeded() {
		setResponsePage(getApplication().getHomePage());
	}

	/**
	 * Sign in form.
	 */
	public final class SignInForm extends StatelessForm<TournamentSignInPanel> {
		private static final long serialVersionUID = 1L;

		public SignInForm() {
			super(SIGN_IN_FORM);

			setModel(new CompoundPropertyModel<TournamentSignInPanel>(TournamentSignInPanel.this));

			add(new ResourceLabel("usernameLabel"));
			add(new TextField<String>("username").setRequired(true));

			add(new ResourceLabel("passwordLabel"));
			add(new PasswordTextField("password").setRequired(true));

			Component rememberMeLabel = new ResourceLabel("rememberMeLabel").setVisible(false);
			Component copyResultInput = new CheckBox("rememberMe").setVisible(false);
			add(rememberMeLabel);
			add(copyResultInput);

			if (includeRememberMe) {
				rememberMeLabel.setVisible(true);
				copyResultInput.setVisible(true);
			}

			add(new TournamentResourceButton("register") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					setResponsePage(new UserEditPage(new User()._setValidity(0)._setRole(UserRole.USER), false, true));
				}
			}.setDefaultFormProcessing(false));

			add(new TournamentResourceButton("reset") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
				}
			}.setDefaultFormProcessing(false));

			add(new TournamentResourceButton("signIn") {

				private static final long serialVersionUID = 1L;

				@Override
				public void submit() {
					IAuthenticationStrategy strategy = getApplication().getSecuritySettings()
					        .getAuthenticationStrategy();

					if (signIn(getUsername(), getPassword())) {
						if (rememberMe == true) {
							strategy.save(username, password);
						} else {
							strategy.remove();
						}
						onSignInSucceeded();
					} else {
						onSignInFailed();
						strategy.remove();
					}
				}
			});
		}
	}
}