package com.maria.ticket;

import com.maria.planet.Planet;
import jakarta.persistence.*;

import java.security.Timestamp;
import java.util.Objects;

@Table(name = "ticket")
@Entity
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public Planet getFrom() {
        return from;
    }

    public void setFrom(Planet from) {
        this.from = from;
    }

    public Planet getTo() {
        return to;
    }

    public void setTo(Planet to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && clientId == ticket.clientId && Objects.equals(createdAt, ticket.createdAt) && Objects.equals(from, ticket.from) && Objects.equals(to, ticket.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, clientId, from, to);
    }
}