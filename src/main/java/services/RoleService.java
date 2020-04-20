package services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

import static services.AuthService.REALM_APP;

public class RoleService {
    private final Keycloak keycloakTokenInstance;

    RoleService(String accessToken) {
        this.keycloakTokenInstance = AuthService.adminAuthByToken(accessToken);
    }

    public void createRole(RoleRepresentation role) {
        try {
            keycloakTokenInstance.realm(REALM_APP).roles().create(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RoleRepresentation> getRoles() {
        try {
            return keycloakTokenInstance.realm(REALM_APP).roles().list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public RoleRepresentation getRoleByName(String name) {
        try {
            return keycloakTokenInstance.realm(REALM_APP).roles().get(name).toRepresentation();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteRole(String name) {
        try {
            keycloakTokenInstance.realm(REALM_APP).roles().deleteRole(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
