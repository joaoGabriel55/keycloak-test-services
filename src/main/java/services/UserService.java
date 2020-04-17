package services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

import static services.AuthService.REALM_APP;


public class UserService {
    private final Keycloak keycloakTokenInstance;

    UserService(String accessToken) {
        this.keycloakTokenInstance = AuthService.adminAuthByToken(accessToken);
    }

    public boolean createUser(UserRepresentation user) {
        return keycloakTokenInstance.realm(REALM_APP).users().create(user).getStatus() == 201;
    }

    public List<UserRepresentation> getUsers() {
        return keycloakTokenInstance.realm(REALM_APP).users().list();
    }

    public UserRepresentation getUserByUsername(String username) {
        return keycloakTokenInstance.realm(REALM_APP).users().search(username).get(0);
    }

    public UserRepresentation getUserById(String id) {
        return keycloakTokenInstance.realm(REALM_APP).users().get(id).toRepresentation();
    }

    public boolean deleteUser(String id) {
        return keycloakTokenInstance.realm(REALM_APP).users().delete(id).getStatus() == 204;
    }
}
