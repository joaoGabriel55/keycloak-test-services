package services;

import org.keycloak.admin.client.resource.AuthorizationResource;
import org.keycloak.admin.client.resource.ResourcePermissionsResource;
import org.keycloak.representations.idm.authorization.DecisionStrategy;
import org.keycloak.representations.idm.authorization.Logic;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;

import javax.ws.rs.core.Response;

public class PermissionService extends KeycloakService {

    public ResourcePermissionRepresentation createClientPermission(
            String clientAppName, String name, String resourceType, String policy
    ) {
        AuthorizationResource authorization = getClient(clientAppName).authorization();

        ResourcePermissionRepresentation permission = new ResourcePermissionRepresentation();
        permission.setName(name);
        permission.setResourceType(resourceType);
        permission.addPolicy(policy);

        permission.setDescription("description");
        permission.setDecisionStrategy(DecisionStrategy.CONSENSUS);
        permission.setLogic(Logic.NEGATIVE);

        ResourcePermissionsResource permissions = authorization.permissions().resource();
        Response response = permissions.create(permission);
        ResourcePermissionRepresentation created = response.readEntity(ResourcePermissionRepresentation.class);
        return created;
    }

    public ResourcePermissionRepresentation getPermissionByName(String clientAppName, String name) {
        try {
            AuthorizationResource authorization = getClient(clientAppName).authorization();
            return authorization.permissions().resource().findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
