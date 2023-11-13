package maria;

import com.maria.client.Client;
import com.maria.client.ClientCrudService;
import com.maria.database.DatabaseMigrationService;
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
    private ClientCrudService clientCrudService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        final String connectionUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        new DatabaseMigrationService().migrateDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        clientCrudService = new ClientCrudService(connection);
        clientCrudService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    void testThatClientCreatedAndGetByIdWorksCorrectly() throws SQLException, ClientCrudService.InvalidNameException, ClientCrudService.InvalidIdException {
        String name = "Sviatoslav Vakarchuk";
        long id = clientCrudService.create(name);
        String created = clientCrudService.getById(id);

        Assertions.assertEquals(name, created);
    }

    @Test
    void testThatInvalidSmallNameThrowsException() {
        Assertions.assertThrows(ClientCrudService.InvalidNameException.class, () -> {
            clientCrudService.nameValidation("v");
        });
    }

    @Test
    void testThatInvalidLongNameThrowsException() {
        String name = "A2";
        for (int i = 3; i < 1005; i++) {
           name += i;
        }
        String finalName = name;
        Assertions.assertThrows(ClientCrudService.InvalidNameException.class, () -> {
            clientCrudService.nameValidation(finalName);
        });
    }

    @Test
    void testThatInvalidNullIdThrowsException() {
        Assertions.assertThrows(ClientCrudService.InvalidIdException.class, () -> {
            clientCrudService.idValidation(0);
        });
    }

    @Test
    void testThatInvalidNegativeIdThrowsException() {
        Assertions.assertThrows(ClientCrudService.InvalidIdException.class, () -> {
            clientCrudService.idValidation(-10);
        });
    }

    @Test
    void testThatSetNameWorksCorrectly() throws SQLException, ClientCrudService.InvalidNameException, ClientCrudService.InvalidIdException {
        String name = "Sviatoslav Vakarchuk";
        long id = clientCrudService.create(name);

        String newName = "Lady Gaga";
        clientCrudService.setName(id, newName);
        String updated = clientCrudService.getById(id);

        Assertions.assertEquals(newName, updated);
    }

    @Test
    public void testDelete() throws SQLException, ClientCrudService.InvalidNameException, ClientCrudService.InvalidIdException {
        String name = "Sviatoslav Vakarchuk";
        long id = clientCrudService.create(name);

        clientCrudService.deleteById(id);

        Assertions.assertNull(clientCrudService.getById(id));
    }

    @Test
    public void getAllTest() throws SQLException, ClientCrudService.InvalidNameException {
        List<Client> expectedClients = new ArrayList<>();
        String name = "Sviatoslav Vakarchuk";
        Client expected = new Client();
        expected.setName(name);
        long id = clientCrudService.create(name);
        expected.setId(id);
        expectedClients.add(expected);

        List<Client> actualClients = clientCrudService.listAll();

        Assertions.assertEquals(expectedClients, actualClients);
    }
}
