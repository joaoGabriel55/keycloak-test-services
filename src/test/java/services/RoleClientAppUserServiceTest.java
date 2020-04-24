package services;

import org.junit.After;
import org.junit.Test;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import static org.junit.Assert.assertTrue;

public class RoleClientAppUserServiceTest extends AbstractServiceTest {

    @Test
    public void assignRoleToClientAppUser() throws Exception {
        RoleClientAppUserService service = new RoleClientAppUserService(accessToken);
        RoleService roleService = new RoleService(accessToken);
        ClientAppService clientAppService = new ClientAppService(accessToken);
        UserService userService = new UserService(accessToken);

        UserRepresentation user = mockUser();
        userService.createUser(user);
        UserRepresentation userSaved = userService.getUserByUsername(user.getUsername());

        clientAppService.createClientApp("test-client");
        ClientRepresentation clientSaved = clientAppService.findClientAppByClientId("test-client");

        RoleRepresentation role1 = new RoleRepresentation();
        role1.setName("test-1");
        roleService.createRoleClientApp(clientSaved.getId(), role1);
        RoleRepresentation roleSaved = roleService.getRoleClientAppByName(clientSaved.getId(), role1.getName());

        boolean assigned = service.assignRoleToClientAppUser(
                clientSaved.getId(), roleSaved.getName(), userSaved.getId()
        );
        assertTrue(assigned);
    }

    @Test
    public void removeRoleToClientAppUser() throws Exception {
        RoleClientAppUserService service = new RoleClientAppUserService(accessToken);
        RoleService roleService = new RoleService(accessToken);
        ClientAppService clientAppService = new ClientAppService(accessToken);
        UserService userService = new UserService(accessToken);

        UserRepresentation user = mockUser();
        userService.createUser(user);
        UserRepresentation userSaved = userService.getUserByUsername(user.getUsername());

        clientAppService.createClientApp("test-client");
        ClientRepresentation clientSaved = clientAppService.findClientAppByClientId("test-client");

        RoleRepresentation role1 = new RoleRepresentation();
        role1.setName("test-1");
        roleService.createRoleClientApp(clientSaved.getId(), role1);
        RoleRepresentation roleSaved = roleService.getRoleClientAppByName(clientSaved.getId(), role1.getName());
        service.assignRoleToClientAppUser(clientSaved.getId(), roleSaved.getName(), userSaved.getId());
        boolean removed = service.removeRoleToClientAppUser(clientSaved.getId(), roleSaved.getName(), userSaved.getId());
        assertTrue(removed);
    }

    @After
    public void after() {
        RoleService roleService = new RoleService(accessToken);
        ClientAppService clientAppService = new ClientAppService(accessToken);
        UserService userService = new UserService(accessToken);

        UserRepresentation user = userService.getUserByUsername(mockUser().getUsername());
        ClientRepresentation client = clientAppService.findClientAppByClientId("test-client");

        clientAppService.deleteClientApp(client.getId());
        roleService.deleteRole("test-1");
        userService.deleteUser(user.getId());
    }

}
