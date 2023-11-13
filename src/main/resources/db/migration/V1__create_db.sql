CREATE TABLE IF NOT EXISTS client (
    client_id IDENTITY PRIMARY KEY,
    cname VARCHAR(200) NOT NULL
);

ALTER TABLE client
    ADD CONSTRAINT cname_values CHECK (LENGTH(cname) >= 3 AND LENGTH(cname) <= 200);

CREATE TABLE IF NOT EXISTS planet (
    planet_id VARCHAR(500) PRIMARY KEY,
    pname VARCHAR(500) NOT NULL
);

ALTER TABLE planet
     ADD CONSTRAINT planet_id_format CHECK (planet_id ~ '^[A-Z0-9]+$');

ALTER TABLE planet
    ADD CONSTRAINT pname_values CHECK (LENGTH(pname) >= 1 AND LENGTH(pname) <= 500);

CREATE TABLE IF NOT EXISTS ticket (
    ticket_id IDENTITY PRIMARY KEY,
    created_at TIMESTAMP,
    client_id BIGINT,
    from_planet_id VARCHAR(500),
    to_planet_id VARCHAR(500),
    FOREIGN KEY(client_id) REFERENCES client(client_id) ON DELETE CASCADE,
    FOREIGN KEY(from_planet_id) REFERENCES planet(planet_id) ON DELETE CASCADE,
    FOREIGN KEY(to_planet_id) REFERENCES planet(planet_id) ON DELETE CASCADE
);

