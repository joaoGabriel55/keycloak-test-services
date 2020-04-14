package services;

import org.keycloak.representations.idm.ClientRepresentation;

public class ClientAppService extends KeycloakService {
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
        return realmResource.clients().create(client).getStatus() == 201;
    }

    public void updateClientApp(String clientId, String id) {
        try {
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
            realmResource.clients().get(id).update(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientRepresentation findClientAppById(String id) {
        try {
            return realmResource.clients().get(id).toRepresentation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClientRepresentation findClientAppByClientId(String clientId) {
        try {
            return realmResource.clients().findByClientId(clientId).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteClientApp(String id) {
        try {
            realmResource.clients().get(id).remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
