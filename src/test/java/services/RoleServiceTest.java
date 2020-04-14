package services;

import org.junit.Test;
import org.keycloak.representations.idm.RoleRepresentation;

import static org.junit.Assert.assertEquals;

public class RoleServiceTest {
    @Test
    public void createRoleTest() {
        RoleService service = new RoleService();
        RoleRepresentation role = new RoleRepresentation();
        role.setName("test-1");
        service.createRole(role);
        String roleNameSaved = service.getRoleByName("test-1").getName();
        assertEquals(role.getName(), roleNameSaved);
        service.deleteRole("test-1");
    }
}
