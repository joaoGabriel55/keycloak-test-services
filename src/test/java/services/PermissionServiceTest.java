package services;

import org.junit.Test;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PermissionServiceTest {

    @Test
    public void getPermissionByNameTest() {
        PermissionService service = new PermissionService();
        ResourcePermissionRepresentation permission = service.getPermissionByName(
                "admin-cli", "Default Permission"
        );
        assertEquals("Default Permission", permission.getName());
    }

    @Test
    public void createClientPermissionTest() {
        PermissionService service = new PermissionService();
        ResourcePermissionRepresentation permissionCreated = service.createClientPermission(
                "admin-cli",
                "test-1",
                "test-resource-type",
                "policy-test");
        assertNotNull(permissionCreated);
    }
}
