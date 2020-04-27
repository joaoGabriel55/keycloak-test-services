package services;

import org.apache.http.HttpStatus;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;

import java.util.LinkedHashMap;
import java.util.Map;

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
        client.setImplicitFlowEnabled(true);
        client.setDirectAccessGrantsEnabled(true);
        client.setServiceAccountsEnabled(true);
        client.setAuthorizationServicesEnabled(true);
        client.setPublicClient(false);
        client.setProtocol("openid-connect");
        client.setFullScopeAllowed(true);
        client.setNodeReRegistrationTimeout(-1);
        Map<String, Boolean> access = new LinkedHashMap<>();
        access.put("view", true);
        access.put("configure", true);
        access.put("manage", true);
        client.setAccess(access);
        return keycloakTokenInstance.realm(REALM_APP).clients().create(client).getStatus() == HttpStatus.SC_CREATED;
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
