package dtos;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public class RolePermission {
    RoleRepresentation role;
    List<RolePermission> rolePermission;

    public RoleRepresentation getRole() {
        return role;
    }

    public List<RolePermission> getRolePermission() {
        return rolePermission;
    }
}
