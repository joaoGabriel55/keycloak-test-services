package services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.AuthorizationResource;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;

import java.util.List;

public class KeycloakService {
    private static final String REALM_APP = "sgeol-middleware";
    private static final String KEYCLOAK_BASE_PATH = "http://localhost:8080/auth";
    private static final String USERNAME = "sgeoladmin";
    private static final String PASSWORD = "123456";
    private static final String CLIENT_ID = "admin-cli";
    private static final String SECRET = "a520e01e-1b73-4cae-b7c0-410080ea524d";

    protected static RealmResource realmResource;

    public static AuthorizationResponse getAuthZInstance() {
        AuthzClient authzClient = AuthzClient.create();
        AuthorizationRequest request = new AuthorizationRequest();
        return authzClient
                .authorization("sgeoladmin", "123456")
                .authorize(request);
    }

    KeycloakService() {
        realmResource = getInstance();
    }

    public static RealmResource getInstance() {
        if (realmResource == null) {
            Keycloak keycloak = Keycloak.getInstance(
                    KEYCLOAK_BASE_PATH, REALM_APP, USERNAME, PASSWORD, CLIENT_ID, SECRET
            );
            return keycloak.realm(REALM_APP);
        }
        return realmResource;
    }

    public ClientResource getClient(String clientName) {
        return getClient(realmResource, clientName);
    }

    public ClientResource getClient(RealmResource realm, String clientName) {
        ClientsResource clients = realm.clients();
        return clients.findByClientId(clientName)
                .stream()
                .map(representation -> clients.get(representation.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Expected client [resource-server-test]"));
    }

}
