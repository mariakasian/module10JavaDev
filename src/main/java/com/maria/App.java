package com.maria;

import com.maria.client.Client;
import com.maria.client.HibernateClientCrudService;
import com.maria.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class App {
    public static void main(String[] args) throws HibernateClientCrudService.InvalidIdException {
        //        System.out.println(HibernateClientCrudService.getById(5).getName());

        Session session = HibernateUtils.getInstance().getSessionFactory().openSession();

//        // TODO create client
        /*Transaction transaction = session.beginTransaction();
            Client client = new Client();
            client.setName("Maria");
            session.persist(client);
        transaction.commit();*/

//         //TODO get client by id or list of clients
        /*Client gettedClient = session.get(Client.class, 1L);
        System.out.println("client ==> " + gettedClient);

        List<Client> clients = session.createQuery("from Client", Client.class).list();
        clients.forEach(cl -> System.out.println("client list ==> " + cl.getId() + " " + cl.getName()));*/

//        // TODO update client
        /*Transaction transaction = session.beginTransaction();
            Client existing = session.get(Client.class, 11L);
            existing.setName("Modified Taras");
            session.persist(existing);
        transaction.commit();*/

//        // TODO delete client
        /*Transaction transaction = session.beginTransaction();
            Client existing = session.get(Client.class, 11L);
            session.remove(existing);
        transaction.commit();*/

        session.close();
    }
}