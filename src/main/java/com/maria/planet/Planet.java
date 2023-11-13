package com.maria.planet;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "planet")
@Entity
@Data
public class Planet {
    @Id
    private String id;

    @Column(name = "pname")
    private String name;
}
