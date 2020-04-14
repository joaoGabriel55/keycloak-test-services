package services;

import org.junit.Test;
import org.keycloak.representations.idm.ClientRepresentation;

import static org.junit.Assert.assertTrue;

public class ClientAppServiceTest {

    @Test
    public void createClientAppTest() {
        ClientAppService service = new ClientAppService();
        assertTrue(service.createClientApp("test-client"));
        ClientRepresentation clientFound = service.findClientAppByClientId("test-client");
        service.deleteClientApp(clientFound.getId());
    }

}
