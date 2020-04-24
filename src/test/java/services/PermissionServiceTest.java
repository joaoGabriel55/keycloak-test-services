package services;

import org.junit.After;
import org.junit.Test;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class PermissionServiceTest extends AbstractServiceTest {

    private Set<String> getPolicies() {
        Set<String> policies = new HashSet<>();
        policies.add("ATUALIZAR");
        policies.add("LEITURA");
        policies.add("REMOVER");
        policies.add("ESCRITA");
        policies.add("GERENCIAR");
        return policies;
    }

    private String getUserId() {
        UserService userService = new UserService(accessToken);
        UserRepresentation userRepresentation = userService.getUserByUsername("test-user");
        return userRepresentation.getId();
    }

    @Test
    public void getPermissionsTest() {
        PermissionService service = new PermissionService(accessToken, "permission-test");
        service.createClientPermission(
                getUserId(),
                "layer-1",
                "policy-test",
                getPolicies()
        );
        service.createClientPermission(
                getUserId(),
                "layer-2",
                "policy-test",
                getPolicies()
        );
        List<PolicyRepresentation> result = service.getPermissions();
        assertTrue(result.size() > 0);
    }

    @Test
    public void getPermissionByNameTest() {
        PermissionService service = new PermissionService(accessToken, "permission-test");
        service.createClientPermission(
                getUserId(),
                "layer-1",
                "policy-test",
                getPolicies()
        );
        PolicyRepresentation permission = service.getPermissionByName("layer-1");
        assertEquals("layer-1", permission.getName());
        assertEquals(5, permission.getPolicies().size());
    }

    @Test
    public void createClientPermissionTest() {
        PermissionService service = new PermissionService(accessToken, "permission-test");
        PolicyRepresentation policyRepresentation = service.createClientPermission(
                getUserId(),
                "layer-1",
                "policy-test",
                getPolicies()
        );
        assertNotNull(policyRepresentation);
    }

    @Test
    public void deletePermissionTest() {
        PermissionService service = new PermissionService(accessToken, "permission-test");
        service.createClientPermission(
                getUserId(),
                "layer-1",
                "policy-test",
                getPolicies()
        );
        boolean deleted = service.deletePermission("layer-1");
        assertTrue(deleted);
    }

    @After
    public void after() {
        PermissionService service = new PermissionService(accessToken, "permission-test");
        service.deletePermission("layer-1");
        service.deletePermission("layer-2");
    }
}
