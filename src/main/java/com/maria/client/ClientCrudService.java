package com.maria.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientCrudService {
    final private PreparedStatement createSt;
    final private PreparedStatement getByIdSt;
    final private PreparedStatement selectMaxIdSt;
    final private PreparedStatement updateSt;
    final private PreparedStatement deleteByIdSt;
    final private PreparedStatement getAllSt;
    final private PreparedStatement clearSt;

    public ClientCrudService(Connection con) throws SQLException {
        createSt = con.prepareStatement(
                "INSERT INTO client (cname) VALUES(?)"
        );

        getByIdSt = con.prepareStatement(
                "SELECT cname FROM client WHERE client_id = ?"
        );

        selectMaxIdSt = con.prepareStatement(
                "SELECT max(client_id) AS maxId FROM client"
        );

        updateSt = con.prepareStatement(
                "UPDATE client SET cname = ? WHERE client_id = ?"
        );

        deleteByIdSt = con.prepareStatement(
                "DELETE FROM client WHERE client_id = ?"
        );

        getAllSt = con.prepareStatement(
                "SELECT client_id, cname FROM client"
        );

        clearSt = con.prepareStatement(
                "DELETE FROM client"
        );
    }

    public long create(String name) throws SQLException, InvalidNameException {
        nameValidation(name);

        createSt.setString(1, name);
        createSt.executeUpdate();

        long id;

        try(ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public String getById(long id) throws SQLException, InvalidIdException {
        idValidation(id);

        getByIdSt.setLong(1, id);

        try(ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }

            return rs.getString("cname");
        }
    }

    public void setName(long id, String name) throws SQLException, InvalidIdException, InvalidNameException {
        idValidation(id);
        nameValidation(name);

        updateSt.setString(1, name);
        updateSt.setLong(2, id);
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException, InvalidIdException {
        idValidation(id);

        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }

    public List<Client> listAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Client> clients = new ArrayList<>();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("client_id"));
                client.setName(rs.getString("cname"));
                clients.add(client);
            }
            return clients;
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public static class InvalidNameException extends Exception {
        public InvalidNameException(String message) {
            super(message);
        }
    }

    public static class InvalidIdException extends Exception {
        public InvalidIdException(String message) {
            super(message);
        }
    }

    public void nameValidation(String name) throws InvalidNameException {
        if (name.length() < 3 || name.length() > 200) {
            throw new InvalidNameException("The name length had to be from 3 to 200 characters.");
        }
    }

    public void idValidation(long id) throws InvalidIdException {
        if (id <= 0) {
            throw new InvalidIdException("Id may be greater than 0.");
        }
    }
}
