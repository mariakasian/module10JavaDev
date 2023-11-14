CREATE TABLE IF NOT EXISTS client (
    id IDENTITY PRIMARY KEY,
    cname VARCHAR(200) NOT NULL
);

ALTER TABLE client
    ADD CONSTRAINT cname_values CHECK (LENGTH(cname) >= 3 AND LENGTH(cname) <= 200);

CREATE TABLE IF NOT EXISTS planet (
    id VARCHAR(500) PRIMARY KEY,
    pname VARCHAR(500) NOT NULL
);

ALTER TABLE planet
     ADD CONSTRAINT id_format CHECK (id ~ '^[A-Z0-9]+$');

ALTER TABLE planet
    ADD CONSTRAINT pname_values CHECK (LENGTH(pname) >= 1 AND LENGTH(pname) <= 500);

CREATE TABLE IF NOT EXISTS ticket (
    id IDENTITY PRIMARY KEY,
    created_at TIMESTAMP,
    client_id BIGINT,
    from_planet_id VARCHAR(500),
    to_planet_id VARCHAR(500),
    FOREIGN KEY(client_id) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY(from_planet_id) REFERENCES planet(id) ON DELETE CASCADE,
    FOREIGN KEY(to_planet_id) REFERENCES planet(id) ON DELETE CASCADE
);

