package services;

import org.junit.Before;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbstractServiceTest {
    private final Keycloak keycloakAuth = AuthService.adminAuth();
    protected String accessToken;

    protected final List<String> permissions = Arrays.asList("ATUALIZAR", "LEITURA", "REMOVER", "ESCRITA", "GERENCIAR");

    protected List<String> getRolePermissions() {
        RoleService service = new RoleService(accessToken);
        List<String> rolePermissionsCreated = new ArrayList<>();
        for (String permission : permissions) {
            RoleRepresentation role = new RoleRepresentation();
            role.setName(permission);
            service.createRole(role);
            if (service.getRoleByName(permission) != null)
                rolePermissionsCreated.add(permission);
        }
        return rolePermissionsCreated;
    }

    protected UserRepresentation mockUser() {
        UserRepresentation user = new UserRepresentation();
        user.setUsername("user-Test");
        user.setFirstName("User");
        user.setLastName("Test");
        user.setEmail("user_test@test.com");
        user.setEnabled(true);
        return user;
    }

    @Before
    public void before() throws Exception {
        accessToken = keycloakAuth.tokenManager().getAccessTokenString();
    }
}
