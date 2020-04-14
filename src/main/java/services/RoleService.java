package services;

import org.keycloak.representations.idm.RoleRepresentation;

public class RoleService extends KeycloakService {
    public void createRole(RoleRepresentation role) {
        try {
            realmResource.roles().create(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RoleRepresentation getRoleByName(String name) {
        try {
            return realmResource.roles().get(name).toRepresentation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteRole(String name) {
        try {
            realmResource.roles().deleteRole(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
