package com.example.demomails.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "metadata")
public class Metadata {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id; // ID de Metadata

    private String cargo;
    private String razon;
    private String localidad;

    // Constructor, getters y setters
}