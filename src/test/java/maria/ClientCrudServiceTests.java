package maria;

import com.maria.client.Client;
import com.maria.client.HibernateClientCrudService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


class ClientCrudServiceTests {
    private Connection connection;
    private HibernateClientCrudService hibernateClientCrudService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        final String connectionUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        new DatabaseMigrationService().migrateDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        hibernateClientCrudService = new HibernateClientCrudService();
        hibernateClientCrudService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    void testThatClientCreatedAndGetByIdWorksCorrectly() throws HibernateClientCrudService.InvalidIdException {
        Client newClient = new Client();
        newClient.setName("Katy Perry");
        long newClientId = hibernateClientCrudService.create(newClient);
        String created = hibernateClientCrudService.getById(newClientId).getName();

        Assertions.assertEquals("Katy Perry", created);
    }

    @Test
    void testThatInvalidSmallNameThrowsException() {
        Assertions.assertThrows(HibernateClientCrudService.InvalidNameException.class, () -> {
            hibernateClientCrudService.nameValidation("v");
        });
    }

    @Test
    void testThatInvalidLongNameThrowsException() {
        String name = "A2";
        for (int i = 3; i < 205; i++) {
           name += i;
        }
        String finalName = name;
        Assertions.assertThrows(HibernateClientCrudService.InvalidNameException.class, () -> {
            hibernateClientCrudService.nameValidation(finalName);
        });
    }

    @Test
    void testThatInvalidNullIdThrowsException() {
        Assertions.assertThrows(HibernateClientCrudService.InvalidIdException.class, () -> {
            hibernateClientCrudService.idValidation(0);
        });
    }

    @Test
    void testThatInvalidNegativeIdThrowsException() {
        Assertions.assertThrows(HibernateClientCrudService.InvalidIdException.class, () -> {
            hibernateClientCrudService.idValidation(-10);
        });
    }

    @Test
    void testThatUpdateNameWorksCorrectly() throws HibernateClientCrudService.InvalidNameException, HibernateClientCrudService.InvalidIdException {
        Client newClient = new Client();
        newClient.setName("Natalie Portman");
        long newClientId = hibernateClientCrudService.create(newClient);

        String newName = "Lady Gaga";
        hibernateClientCrudService.updateName(newClientId, newName);

        String updated = hibernateClientCrudService.getById(newClientId).getName();

        Assertions.assertEquals(newName, updated);
    }

    @Test
    public void testDelete() throws HibernateClientCrudService.InvalidIdException {
        Client newClient = new Client();
        newClient.setName("Demi Moore");
        long newClientId = hibernateClientCrudService.create(newClient);

        hibernateClientCrudService.deleteById(newClientId);

        Assertions.assertNull(hibernateClientCrudService.getById(newClientId));
    }

    @Test
    public void getAllTest() throws SQLException, HibernateClientCrudService.InvalidNameException {
        List<Client> expectedClients = new ArrayList<>();

        Client client1 = new Client();
        client1.setName("Kit Harington");
        long client1Id = hibernateClientCrudService.create(client1);
        client1.setId(client1Id);
        expectedClients.add(client1);

        Client client2 = new Client();
        client2.setName("Meghan Markle");
        long client2Id = hibernateClientCrudService.create(client2);
        client2.setId(client2Id);
        expectedClients.add(client2);

        List<Client> actualClients = hibernateClientCrudService.getAll();

        Assertions.assertEquals(expectedClients, actualClients);
    }
}
