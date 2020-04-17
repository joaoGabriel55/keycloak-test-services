package services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;

import static services.AuthService.REALM_APP;

public class ClientAppService {

    private final Keycloak keycloakTokenInstance;

    ClientAppService(String accessToken) {
        this.keycloakTokenInstance = AuthService.adminAuthByToken(accessToken);
    }

    public boolean createClientApp(String clientId) {
        ClientRepresentation client = new ClientRepresentation();
        client.setClientId(clientId);
        client.setEnabled(true);
        client.setClientAuthenticatorType("client-secret");
        client.setStandardFlowEnabled(true);
        client.setDirectAccessGrantsEnabled(true);
        client.setPublicClient(true);
        client.setProtocol("openid-connect");
        client.setFullScopeAllowed(true);
        client.setNodeReRegistrationTimeout(-1);
        String[] defaultClientScopes = {"web-origins", "role_list", "profile", "roles", "email"};
        client.setDefaultRoles(defaultClientScopes);
        return keycloakTokenInstance.realm(REALM_APP).clients().create(client).getStatus() == 201;
    }

    public void updateClientApp(String id, ClientRepresentation clientUpdated) {
        try {
            keycloakTokenInstance.realm(REALM_APP).clients().get(id).update(clientUpdated);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientRepresentation findClientAppById(String id) {
        try {
            return keycloakTokenInstance.realm(REALM_APP).clients().get(id).toRepresentation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClientRepresentation findClientAppByClientId(String clientId) {
        try {
            return keycloakTokenInstance.realm(REALM_APP).clients().findByClientId(clientId).get(0);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteClientApp(String id) {
        try {
            keycloakTokenInstance.realm(REALM_APP).clients().get(id).remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
