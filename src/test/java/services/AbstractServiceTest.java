package services;

import org.junit.Before;
import org.keycloak.admin.client.Keycloak;

public class AbstractServiceTest {
    private final Keycloak keycloakAuth = AuthService.adminAuth();
    protected String accessToken;

    @Before
    public void before() throws Exception {
        accessToken = keycloakAuth.tokenManager().getAccessTokenString();
    }
}
