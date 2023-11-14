package com.maria.client;


import com.maria.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class HibernateClientCrudService {

    public static long create(Client client) {
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
            return client.getId();
        }
    }

    public static Client getById(long id) throws InvalidIdException {
        idValidation(id);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            NativeQuery<Client> query = session.createNativeQuery(
                    "SELECT cname FROM client WHERE id = :clientId",
                    Client.class
            );

            query.setParameter("clientId", id);
            return query.stream().findFirst().orElse(null);

//            return session.get(Client.class, id);
        }
    }

    public static void updateName(long id, String name) throws InvalidIdException, InvalidNameException {
        idValidation(id);
        nameValidation(name);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Client> query = session.createNativeQuery(
                    "UPDATE client SET cname = :clientName WHERE id = :clientId",
                    Client.class
            );
            query.setParameter("clientName", name);
            query.setParameter("clientId", id);

            session.persist(query);
            transaction.commit();
        }
    }

    public static void deleteById(long id) throws InvalidIdException {
        idValidation(id);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Client> query = session.createNativeQuery(
                    "DELETE FROM client WHERE id = :clientId",
                    Client.class
            );
            query.setParameter("clientId", id);

            session.persist(query);
            transaction.commit();
        }
    }

    public static List<Client> getAll() {
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return session.createNativeQuery("SELECT id, cname FROM client", Client.class).list();

            /*NativeQuery<Client> query = session.createNativeQuery(
                    "SELECT client_id, cname FROM client",
                    Client.class
            );*/
        }
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

    public static void nameValidation(String name) throws InvalidNameException {
        if (name.length() < 3 || name.length() > 200) {
            throw new InvalidNameException("The name length had to be from 3 to 200 characters.");
        }
    }

    public static void idValidation(long id) throws InvalidIdException {
        if (id <= 0) {
            throw new InvalidIdException("Id may be greater than 0.");
        }
    }

    // For testing
    public static void clear() {
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Client> query = session.createNativeQuery(
                    "DELETE FROM client",
                    Client.class
            );
            session.persist(query);
            transaction.commit();
        }
    }
}
