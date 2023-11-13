package com.maria.database;

import org.flywaydb.core.Flyway;

public class DatabaseMigrationService {
    public static void migrateDb(String conUrl) {
        Flyway flyway = Flyway
                .configure()
                .dataSource(conUrl, null, null)
                .load();

        flyway.migrate();
    }
}