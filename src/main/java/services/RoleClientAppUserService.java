package services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.Collections;
import java.util.List;

import static services.AuthService.REALM_APP;

public class RoleClientAppUserService {

    private final Keycloak keycloakTokenInstance;

    RoleClientAppUserService(String accessToken) {
        this.keycloakTokenInstance = AuthService.adminAuthByToken(accessToken);
    }

    public boolean assignRoleToClientAppUser(String clientId, String roleName, String userId) throws Exception {
        try {
            boolean assigned = false;
            RoleRepresentation clientRole = keycloakTokenInstance.realm(REALM_APP)
                    .clients().get(clientId)
                    .roles().get(roleName).toRepresentation();
            UsersResource usersResource = keycloakTokenInstance.realm(REALM_APP).users();
            RoleScopeResource roleScopeResource = usersResource.get(userId).roles().clientLevel(clientId);
            roleScopeResource.add(Collections.singletonList(clientRole));
            List<RoleRepresentation> userRoles = roleScopeResource.listAll();
            for (RoleRepresentation role : userRoles) {
                if (role.getId().equals(clientRole.getId())) {
                    assigned = true;
                    break;
                }
            }
            return assigned;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public boolean removeRoleToClientAppUser(String clientId, String roleName, String userId) throws Exception {
        try {
            boolean assigned = false;
            RoleRepresentation clientRole = keycloakTokenInstance.realm(REALM_APP)
                    .clients().get(clientId)
                    .roles().get(roleName).toRepresentation();
            UsersResource usersResource = keycloakTokenInstance.realm(REALM_APP).users();
            RoleScopeResource roleScopeResource = usersResource.get(userId).roles().clientLevel(clientId);
            roleScopeResource.remove(Collections.singletonList(clientRole));
            List<RoleRepresentation> userRoles = roleScopeResource.listAll();
            for (RoleRepresentation role : userRoles) {
                if (role.getId().equals(clientRole.getId())) {
                    assigned = true;
                    break;
                }
            }
            return !assigned;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
