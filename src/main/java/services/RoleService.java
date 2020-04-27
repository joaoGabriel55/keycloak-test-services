package services;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static services.AuthService.KEYCLOAK_BASE_PATH;
import static services.AuthService.REALM_APP;
import static utils.ApacheHttpClient.getHttpClientInstance;

public class RoleService {
    private final Keycloak keycloakTokenInstance;

    RoleService(String accessToken) {
        this.keycloakTokenInstance = AuthService.adminAuthByToken(accessToken);
    }

    private boolean assignRolePermissions(String roleId, List<RoleRepresentation> rolePermissions, String token) {
        try {
            String uri = KEYCLOAK_BASE_PATH + "/admin/realms/" + REALM_APP + "/roles-by-id/" + roleId + "/composites";
            HttpPost request = new HttpPost(uri);
            request.setHeader("Authorization", "Bearer " + token);
            String jsonArray = new JSONArray(rolePermissions).toString();
            StringEntity entity = new StringEntity(jsonArray, ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            HttpResponse response = getHttpClientInstance().execute(request);
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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

    public boolean createRolePermissionClientApp(
            String clientId, RoleRepresentation role, List<String> permissions, String token) {
        try {
            RolesResource rolesResource = keycloakTokenInstance.realm(REALM_APP).clients().get(clientId).roles();
            rolesResource.create(role);
            RoleRepresentation roleSaved = rolesResource.get(role.getName()).toRepresentation();
            if (roleSaved != null) {
                List<RoleRepresentation> rolePermissions = new ArrayList<>();
                for (String permission : permissions) {
                    RoleRepresentation rolePermission = keycloakTokenInstance.realm(REALM_APP).roles().get(permission).toRepresentation();
                    if (rolePermission != null) {
                        rolePermissions.add(rolePermission);
                    }
                }
                return assignRolePermissions(roleSaved.getId(), rolePermissions, token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
