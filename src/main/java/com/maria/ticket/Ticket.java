package com.maria.ticket;

import com.maria.planet.Planet;
import jakarta.persistence.*;
import lombok.Data;

import java.security.Timestamp;

@Table(name = "ticket")
@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "client_id")
    private long clientId;

    @Column(name = "from_planet_id")
    @Enumerated(EnumType.STRING)
    private Planet from;

    @Column(name = "to_planet_id")
    @Enumerated(EnumType.STRING)
    private Planet to;
}