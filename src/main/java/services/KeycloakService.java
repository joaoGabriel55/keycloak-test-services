package services;

import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;

public class KeycloakService {
    private static KeycloakService instance;

    public static KeycloakService getInstance() {
        if (instance == null)
            return instance = new KeycloakService();
        return instance;
    }

    public static AuthorizationResponse getAuthZInstance(AuthorizationRequest request) {
        AuthzClient authzClient = AuthzClient.create();
        return authzClient
                .authorization("sgeoladmin", "123456")
                .authorize(request);
    }

    public ClientResource getClient(RealmResource realm, String clientName) {
        ClientsResource clients = realm.clients();
        return clients.findByClientId(clientName)
                .stream()
                .map(representation -> clients.get(representation.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Expected client [" + clientName + "]"));
    }

}
