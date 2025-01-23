package com.plants.db.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Plants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String namePlant;
    private String descriptionPlant;
    private String location;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private Users user;

    public String getDescriptionPlant() {
        return descriptionPlant;
    }

    public void setDescriptionPlant(String descriptionPlant) {
        this.descriptionPlant = descriptionPlant;
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

    public String getNamePlant() {
        return namePlant;
    }

    public void setNamePlant(String name) {
        this.namePlant = name;
    }
}
