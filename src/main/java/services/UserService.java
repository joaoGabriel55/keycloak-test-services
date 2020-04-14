package services;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public class UserService extends KeycloakService {

    public boolean createUser(UserRepresentation user) {
        return realmResource.users().create(user).getStatus() == 201;
    }

    public List<UserRepresentation> getUsers() {
        return realmResource.users().list();
    }

    public UserRepresentation getUserByUsername(String username) {
        return realmResource.users().search(username).get(0);
    }

    public UserRepresentation getUserById(String id) {
        return realmResource.users().get(id).toRepresentation();
    }

    public boolean deleteUser(String id) {
        return realmResource.users().delete(id).getStatus() == 204;
    }
}
