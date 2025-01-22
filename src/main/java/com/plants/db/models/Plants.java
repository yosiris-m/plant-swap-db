package com.plants.db.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Plants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name_plant;
    private String description_plant;
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users user;

    public String getDescription_plant() {
        return description_plant;
    }

    public void setDescription_plant(String description_plant) {
        this.description_plant = description_plant;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName_plant() {
        return name_plant;
    }

    public void setName_plant(String name) {
        this.name_plant = name;
    }
}
