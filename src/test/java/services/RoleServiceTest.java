package services;

import org.junit.Test;
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
    public void deleteRoleTest() {
        RoleService service = new RoleService(accessToken);
        RoleRepresentation role = new RoleRepresentation();
        role.setName("test-1");
        service.createRole(role);
        service.deleteRole("test-1");
        RoleRepresentation roleDeleted = service.getRoleByName("test-1");
        assertNull(roleDeleted);
    }

}
