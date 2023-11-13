package com.maria;

import com.maria.database.Database;

import java.io.IOException;

import static com.maria.database.Database.conUrl;
import static com.maria.database.DatabaseMigrationService.migrateDb;

public class App {
    public static void main(String[] args) throws IOException {
        Database db = Database.getInstance();
        migrateDb(conUrl);

        db.connectionClose();
    }
}