package services;

import org.junit.Before;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;

public class AbstractServiceTest {
    private final Keycloak keycloakAuth = AuthService.adminAuth();
    protected String accessToken;

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
