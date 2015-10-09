package org.toursys.processor.service.user;

import java.util.List;

import net.sf.lightair.LightAirSpringRunner;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.toursys.repository.model.User;
import org.toursys.repository.model.UserRole;

@RunWith(LightAirSpringRunner.class)
@ContextConfiguration(locations = { "/spring/application-context-test.xml" })
@Setup.List({ @Setup("/clear-all.xml"), @Setup() })
public class UserIT {

    @Autowired
    private UserService userService;

    @Test
    @Verify("createUserTest-verify.xml")
    public void createUserTest() {
        User user = userService.createUser(new User("email", "username", "password", UserRole.USER, 1));
        Assert.assertNotNull(user.getId());
    }

    @Test
    @Verify("getAllUsersTest-verify.xml")
    public void getAllUsersTest() {
        List<User> users = userService.getAllUsers();
        Assert.assertSame(1, users.size());
    }

    @Test
    @Verify("getAllUsersTest-verify.xml")
    public void getUserTest() {
        User user = userService.getUser(new User()._setUserName("admin"));
        Assert.assertEquals(1, user.getId().intValue());
    }

    @Test
    @Verify("updateUserTest-verify.xml")
    public void updateUserTest() {
        int count = userService.updateUser(new User()._setId(1)._setEmail("emailEdit")._setName("nameEdit")
                ._setPassword("passEdit")._setPlatnost(0)._setRole(UserRole.USER)._setSurname("surnameEdit")
                ._setUserName("usernameEdit"));
        Assert.assertNotSame(0, count);
    }
}
