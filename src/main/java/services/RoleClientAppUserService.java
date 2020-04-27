package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.RolePermissionDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static services.AuthService.KEYCLOAK_BASE_PATH;
import static services.AuthService.REALM_APP;
import static utils.ApacheHttpClient.getHttpClientInstance;
import static utils.ApacheHttpClient.readResponseBody;

public class RoleClientAppUserService {

    private final Keycloak keycloakTokenInstance;

    RoleClientAppUserService(String accessToken) {
        this.keycloakTokenInstance = AuthService.adminAuthByToken(accessToken);
    }

    @SuppressWarnings("ALL")
    private List<LinkedHashMap<String, Object>> getRolesFromCompositeRoles(String roleId, String token) {
        String uri = KEYCLOAK_BASE_PATH + "/admin/realms/" + REALM_APP + "/roles-by-id/" + roleId + "/composites/realm";
        HttpGet request = new HttpGet(uri);
        request.setHeader("Authorization", "Bearer " + token);
        try {
            HttpResponse response = getHttpClientInstance().execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                ObjectMapper mapper = new ObjectMapper();
                return (List<LinkedHashMap<String, Object>>) mapper.readValue(
                        readResponseBody(response.getEntity().getContent()), List.class
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<RolePermissionDTO> getRolesComposedByUserAndClientApp(String clientId, String userId, String token) {
        try {
            List<RolePermissionDTO> rolePermissions = new ArrayList<>();
            UsersResource usersResource = keycloakTokenInstance.realm(REALM_APP).users();
            RoleScopeResource roleScopeResource = usersResource.get(userId).roles().clientLevel(clientId);
            for (RoleRepresentation role : roleScopeResource.listEffective()) {
                RolePermissionDTO dto = new RolePermissionDTO();
                dto.setRole(role);
                dto.setRolePermission(getRolesFromCompositeRoles(role.getId(), token));
                rolePermissions.add(dto);
            }
            return rolePermissions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
