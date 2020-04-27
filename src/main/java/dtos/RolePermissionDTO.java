package dtos;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.LinkedHashMap;
import java.util.List;

public class RolePermissionDTO {
    RoleRepresentation role;
    List<LinkedHashMap<String, Object>> rolePermission;

    public RoleRepresentation getRole() {
        return role;
    }

    public List<LinkedHashMap<String, Object>> getRolePermission() {
        return rolePermission;
    }

    public void setRole(RoleRepresentation role) {
        this.role = role;
    }

    public void setRolePermission(List<LinkedHashMap<String, Object>> rolePermission) {
        this.rolePermission = rolePermission;
    }
}
