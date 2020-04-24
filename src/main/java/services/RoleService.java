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

    public void createRoleClientApp(String clientId, RoleRepresentation role) {
        try {
            keycloakTokenInstance.realm(REALM_APP).clients().get(clientId).roles().create(role);
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

    public List<RoleRepresentation> getRolesClientApp(String clientId) {
        try {
            return keycloakTokenInstance.realm(REALM_APP)
                    .clients().get(clientId).roles().list();
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

    public RoleRepresentation getRoleClientAppByName(String clientId, String name) {
        try {
            return keycloakTokenInstance.realm(REALM_APP)
                    .clients().get(clientId).roles().get(name).toRepresentation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteRole(String name) {
        try {
            if (getRoleByName(name) != null) {
                keycloakTokenInstance.realm(REALM_APP).roles().deleteRole(name);
                return getRoleByName(name) == null;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRoleClientApp(String clientId, String name) {
        try {
            if (getRoleClientAppByName(clientId, name) != null) {
                keycloakTokenInstance.realm(REALM_APP)
                        .clients().get(clientId).roles().deleteRole(name);
                return getRoleClientAppByName(clientId, name) == null;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
