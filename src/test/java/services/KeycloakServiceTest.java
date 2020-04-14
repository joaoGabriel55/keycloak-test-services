package services;

import org.junit.Test;
import org.keycloak.admin.client.resource.RealmResource;

import static org.junit.Assert.assertNotNull;

public class KeycloakServiceTest {

    @Test
    public void getInstanceTest() {
        RealmResource realmRepresentation = KeycloakService.getInstance();
        assertNotNull(realmRepresentation);
    }

}
