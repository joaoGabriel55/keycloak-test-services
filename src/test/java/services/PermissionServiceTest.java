package services;

import org.junit.Test;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PermissionServiceTest extends AbstractServiceTest {
    @Test
    public void getPermissionByNameTest() {
        PermissionService service = new PermissionService(accessToken);
        ResourcePermissionRepresentation permission = service.getPermissionByName(
                "permission-test", "test-resource"
        );
        assertEquals("test-resource", permission.getName());
    }

    @Test
    public void createClientPermissionTest() {
        PermissionService service = new PermissionService(accessToken);
        ResourcePermissionRepresentation permissionCreated = service.createClientPermission(
                "permission-test",
                "test-1",
                "test-resource-type",
                "policy-test");
        assertNotNull(permissionCreated);
    }
}
