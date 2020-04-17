package services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.AuthorizationResource;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;

import static services.AuthService.REALM_APP;

public class PermissionService {
    private final KeycloakService keycloakService = KeycloakService.getInstance();
    private final Keycloak keycloakTokenInstance;

    PermissionService(String accessToken) {
        this.keycloakTokenInstance = AuthService.adminAuthByToken(accessToken);
    }

    // TODO: I NEED TO RESOLVE THAT!!!
    public ResourcePermissionRepresentation createClientPermission(
            String clientAppName, String name, String resourceType, String policy
    ) {
//        ClientResource clientResource = keycloakService.getClient(keycloakTokenInstance.realm(clientAppName), clientAppName);
//        ClientRepresentation clientRepresentation = clientResource.toRepresentation();
//        AuthorizationResource authorization = clientResource.authorization();
//        JSPolicyRepresentation policyRepresentation = new JSPolicyRepresentation();
//
//        policyRepresentation.setName(policy);
//        policyRepresentation.setCode("$evaluation.grant();");
//        Response response = authorization.policies().js().create(policyRepresentation);

//        AuthorizationResource authorization = getClient(clientAppName).authorization();
//
//        ResourcePermissionRepresentation permission = new ResourcePermissionRepresentation();
//        permission.setName(name);
//        permission.setResourceType(resourceType);
//        permission.addPolicy(policy);
//
//        permission.setDescription("description");
//        permission.setDecisionStrategy(DecisionStrategy.CONSENSUS);
//        permission.setLogic(Logic.NEGATIVE);
//
//        ResourcePermissionsResource permissions = authorization.permissions().resource();
//        Response response = permissions.create(permission);
//        ResourcePermissionRepresentation created = response.readEntity(ResourcePermissionRepresentation.class);
        return null;
    }

    public ResourcePermissionRepresentation getPermissionByName(String clientAppName, String name) {
        try {
            AuthorizationResource authorization = keycloakService.getClient(
                    keycloakTokenInstance.realm(REALM_APP), clientAppName
            ).authorization();
            return authorization.permissions().resource().findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deletePermission(String clientAppName) {
        // TODO: Delete
    }
}
