package services;

import org.junit.Test;
import org.keycloak.representations.idm.ClientRepresentation;

import static org.junit.Assert.*;

public class ClientAppServiceTest extends AbstractServiceTest {

    @Test
    public void createClientAppTest() {
        ClientAppService service = new ClientAppService(accessToken);
        assertTrue(service.createClientApp("test-client"));
        ClientRepresentation clientFound = service.findClientAppByClientId("test-client");
        service.deleteClientApp(clientFound.getId());
    }

    @Test
    public void updateClientAppTest() {
        ClientAppService service = new ClientAppService(accessToken);
        service.createClientApp("test-client");
        ClientRepresentation clientFound = service.findClientAppByClientId("test-client");
        clientFound.setDescription("description updated");
        service.updateClientApp(clientFound.getId(), clientFound);
        ClientRepresentation clientFoundUpdated = service.findClientAppByClientId("test-client");
        assertEquals(clientFound.getDescription(), clientFoundUpdated.getDescription());
        service.deleteClientApp(clientFound.getId());
    }

    @Test
    public void findClientAppByIdTest() {
        ClientAppService service = new ClientAppService(accessToken);
        service.createClientApp("test-client");
        ClientRepresentation clientFound = service.findClientAppByClientId("test-client");
        ClientRepresentation clientById = service.findClientAppById(clientFound.getId());
        assertNotNull(clientById);
        service.deleteClientApp(clientFound.getId());
    }

    @Test
    public void findClientAppByClientId() {
        ClientAppService service = new ClientAppService(accessToken);
        service.createClientApp("test-client");
        ClientRepresentation clientFound = service.findClientAppByClientId("test-client");
        assertNotNull(clientFound);
        service.deleteClientApp(clientFound.getId());
    }

    @Test
    public void deleteClientApp() {
        ClientAppService service = new ClientAppService(accessToken);
        service.createClientApp("test-client");
        ClientRepresentation clientFound = service.findClientAppByClientId("test-client");
        service.deleteClientApp(clientFound.getId());
        clientFound = service.findClientAppByClientId("test-client");
        assertNull(clientFound);
    }

}
