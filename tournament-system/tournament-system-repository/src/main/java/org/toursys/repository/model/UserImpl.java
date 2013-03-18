package org.toursys.repository.model;

public class UserImpl extends User {

    private static final long serialVersionUID = 1L;

    private String confirmPassword;

    public UserImpl(User user) {
        super.setEmail(user.getEmail());
        super.setId(user.getId());
        super.setName(user.getName());
        super.setPassword(user.getPassword());
        super.setPlatnost(user.getPlatnost());
        super.setPlayers(user.getPlayers());
        super.setRole(user.getRole());
        super.setSeasons(user.getSeasons());
        super.setSurname(user.getSurname());
        super.setUserName(user.getUserName());
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
