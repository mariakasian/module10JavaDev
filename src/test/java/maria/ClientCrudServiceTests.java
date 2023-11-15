package maria;

import com.maria.client.Client;
import com.maria.client.HibernateClientCrudService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ClientCrudServiceTests {
    @Test
    void testThatClientCreatedCorrectly() throws HibernateClientCrudService.InvalidIdException {
        Client newClient = new Client();
        newClient.setName("Katy Perry");
        long newClientId = HibernateClientCrudService.create(newClient);
        String created = HibernateClientCrudService.getById(newClientId).getName();

        assertEquals("Katy Perry", created);
    }

    @Test
    void testThatGetByIdCorrectly() throws HibernateClientCrudService.InvalidIdException {
        long id = 1;
        Client client = HibernateClientCrudService.getById(id);
        assertNotNull(client);
    }

    @Test
    void testThatUpdateNameWorksCorrectly() throws HibernateClientCrudService.InvalidNameException, HibernateClientCrudService.InvalidIdException {
        Client newClient = new Client();
        newClient.setName("Natalie Portman");
        long newClientId = HibernateClientCrudService.create(newClient);

        String newName = "Lady Gaga";
        HibernateClientCrudService.updateName(newClientId, newName);

        String updated = HibernateClientCrudService.getById(newClientId).getName();

        assertEquals(newName, updated);
    }

    @Test
    void deleteById() throws HibernateClientCrudService.InvalidIdException {
        long id = 1;
        assertDoesNotThrow(() -> HibernateClientCrudService.deleteById(id));
        assertNull(HibernateClientCrudService.getById(id));
    }

    @Test
    public void getAllTest() {
        HibernateClientCrudService.clear();
        List<Client> expectedClients = new ArrayList<>();

        Client client1 = new Client();
        client1.setName("Kit Harington");
        long client1Id = HibernateClientCrudService.create(client1);
        client1.setId(client1Id);
        expectedClients.add(client1);

        Client client2 = new Client();
        client2.setName("Meghan Markle");
        long client2Id = HibernateClientCrudService.create(client2);
        client2.setId(client2Id);
        expectedClients.add(client2);

        List<Client> actualClients = HibernateClientCrudService.getAll();

        assertEquals(expectedClients, actualClients);
    }

    @Test
    void testThatInvalidSmallNameThrowsException() {
        assertThrows(HibernateClientCrudService.InvalidNameException.class, () ->
                HibernateClientCrudService.nameValidation("v"));
    }

    @Test
    void testThatInvalidLongNameThrowsException() {
        StringBuilder name = new StringBuilder("A2");
        for (int i = 3; i < 205; i++) name.append(i);
        String finalName = name.toString();
        assertThrows(HibernateClientCrudService.InvalidNameException.class, () ->
                HibernateClientCrudService.nameValidation(finalName));
    }

    @Test
    void testThatInvalidNullIdThrowsException() {
        assertThrows(HibernateClientCrudService.InvalidIdException.class, () ->
                HibernateClientCrudService.idValidation(0));
    }

    @Test
    void testThatInvalidNegativeIdThrowsException() {
        assertThrows(HibernateClientCrudService.InvalidIdException.class, () ->
                HibernateClientCrudService.idValidation(-10));
    }
}
