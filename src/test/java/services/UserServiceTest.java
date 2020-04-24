package services;

import org.junit.Test;
import org.keycloak.representations.idm.UserRepresentation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest extends AbstractServiceTest {

    @Test
    public void createUserTest() {
        UserService service = new UserService(accessToken);
        UserRepresentation user = mockUser();
        assertTrue(service.createUser(user));
        UserRepresentation userFound = service.getUserByUsername(user.getUsername());
        service.deleteUser(userFound.getId());
    }

    @Test
    public void getUsersTest() {
        UserService service = new UserService(accessToken);
        UserRepresentation user = mockUser();
        service.createUser(user);
        assertTrue(service.getUsers().size() > 0);
        UserRepresentation userFound = service.getUserByUsername(user.getUsername());
        service.deleteUser(userFound.getId());
    }

    @Test
    public void getUserByIdTest() {
        UserService service = new UserService(accessToken);
        UserRepresentation user = mockUser();
        service.createUser(user);
        UserRepresentation userFound = service.getUserByUsername(user.getUsername());
        assertNotNull(service.getUserById(userFound.getId()));
        service.deleteUser(userFound.getId());
    }

    @Test
    public void getUserByUsernameTest() {
        UserService service = new UserService(accessToken);
        UserRepresentation user = mockUser();
        service.createUser(user);
        UserRepresentation userFound = service.getUserByUsername(user.getUsername());
        assertNotNull(userFound);
        service.deleteUser(userFound.getId());
    }

    @Test
    public void deleteUserTest() {
        UserService service = new UserService(accessToken);
        UserRepresentation user = mockUser();
        service.createUser(user);
        UserRepresentation userFound = service.getUserByUsername(user.getUsername());
        assertTrue(service.deleteUser(userFound.getId()));
    }
}
