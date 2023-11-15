package com.maria;

import com.maria.client.Client;
import com.maria.client.HibernateClientCrudService;

import java.util.List;

import static com.maria.client.HibernateClientCrudService.*;

public class App {
    public static void main(String[] args) throws HibernateClientCrudService.InvalidIdException, InvalidNameException {
        Client newClient = new Client();
        newClient.setName("Charles Chaplin");
        System.out.println("Created new client with Id = " + create(newClient));
        System.out.println("=================");
        List<Client> clients = getAll();
        System.out.println("Client`s list:");
        clients.forEach(client -> System.out.println(client.getId() + " " + client.getName()));
        System.out.println("=================");
        updateName(1, "New Name");
        System.out.println("After updating client with id = 1: " + getById(1).getName());
        System.out.println("=================");
        deleteById(1);
        System.out.println("After deleting client with id = 1: " + getById(1));

    }
}