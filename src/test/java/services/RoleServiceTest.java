package services;

import org.junit.After;
import org.junit.Test;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

import static org.junit.Assert.*;

public class RoleServiceTest extends AbstractServiceTest {

    @Test
    public void createRoleTest() {
        RoleService service = new RoleService(accessToken);
        RoleRepresentation role = new RoleRepresentation();
        role.setName("test-1");
        service.createRole(role);
        String roleNameSaved = service.getRoleByName("test-1").getName();
        assertEquals(role.getName(), roleNameSaved);
        service.deleteRole("test-1");
    }

    @Test
    public void createRoleClientApp() {
        RoleService service = new RoleService(accessToken);
        ClientAppService clientAppService = new ClientAppService(accessToken);

        clientAppService.createClientApp("test-client");
        ClientRepresentation clientSaved = clientAppService.findClientAppByClientId("test-client");

        RoleRepresentation role = new RoleRepresentation();
        role.setName("test-1");

        service.createRoleClientApp(clientSaved.getId(), role);
        String roleNameSaved = service.getRoleClientAppByName(clientSaved.getId(), "test-1").getName();

        assertEquals(role.getName(), roleNameSaved);
        service.deleteRoleClientApp(clientSaved.getId(), "test-1");
    }

    @Test
    public void getRolesTest() {
        RoleService service = new RoleService(accessToken);
        RoleRepresentation role1 = new RoleRepresentation();
        role1.setName("test-1");
        RoleRepresentation role2 = new RoleRepresentation();
        role2.setName("test-2");
        service.createRole(role1);
        service.createRole(role2);
        assertTrue(service.getRoles().size() != 0);
        service.deleteRole(role1.getName());
        service.deleteRole(role2.getName());
    }

    @Test
    public void getRoleByNameTest() {
        RoleService service = new RoleService(accessToken);
        RoleRepresentation role = new RoleRepresentation();
        role.setName("test-1");
        service.createRole(role);
        RoleRepresentation roleSaved = service.getRoleByName("test-1");
        assertNotNull(roleSaved);
        service.deleteRole("test-1");
    }

    @Test
    public void getRoleClientAppByNameTest() {
        RoleService service = new RoleService(accessToken);
        ClientAppService clientAppService = new ClientAppService(accessToken);

        clientAppService.createClientApp("test-client");
        ClientRepresentation clientSaved = clientAppService.findClientAppByClientId("test-client");

        RoleRepresentation role = new RoleRepresentation();
        role.setName("test-1");

        service.createRoleClientApp(clientSaved.getId(), role);
        RoleRepresentation roleSaved = service.getRoleClientAppByName(clientSaved.getId(), "test-1");

        assertNotNull(roleSaved);
        service.deleteRoleClientApp(clientSaved.getId(), "test-1");
    }

    @Test
    public void getRolesClientAppTest() {
        RoleService roleService = new RoleService(accessToken);
        ClientAppService clientAppService = new ClientAppService(accessToken);

        clientAppService.createClientApp("test-client");
        ClientRepresentation clientSaved = clientAppService.findClientAppByClientId("test-client");

        RoleRepresentation role1 = new RoleRepresentation();
        RoleRepresentation role2 = new RoleRepresentation();
        role1.setName("test-1");
        role2.setName("test-2");

        roleService.createRoleClientApp(clientSaved.getId(), role1);
        roleService.createRoleClientApp(clientSaved.getId(), role2);

        assertTrue(roleService.getRolesClientApp(clientSaved.getId()).size() > 0);
    }

    @Test
    public void deleteRoleTest() {
        RoleService service = new RoleService(accessToken);
        RoleRepresentation role = new RoleRepresentation();
        role.setName("test-1");
        service.createRole(role);

        boolean deleted = service.deleteRole("test-1");
        assertTrue(deleted);
    }

    @Test
    public void deleteRoleClientAppTest() {
        RoleService service = new RoleService(accessToken);
        ClientAppService clientAppService = new ClientAppService(accessToken);

        clientAppService.createClientApp("test-client");
        ClientRepresentation clientSaved = clientAppService.findClientAppByClientId("test-client");

        RoleRepresentation role = new RoleRepresentation();
        role.setName("test-1");
        service.createRoleClientApp(clientSaved.getId(), role);

        boolean deleted = service.deleteRoleClientApp(clientSaved.getId(), "test-1");
        assertTrue(deleted);
    }

    @After
    public void tearDown() {
        ClientAppService clientAppService = new ClientAppService(accessToken);
        ClientRepresentation client = clientAppService.findClientAppByClientId("test-client");
        if (client != null)
            clientAppService.deleteClientApp(client.getId());
    }
}
