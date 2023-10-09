package com.example.demomails.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "documento")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String docname;

    @Column(length = 10485760) //longitud maxima del documento en Base64
    private String documento;
    private String url_callback;
    private String usuarioid;

    private LocalDateTime fecha;

    @ElementCollection
    @CollectionTable(name = "emails", joinColumns = @JoinColumn(name = "documento_id"))
    @Column(name = "email")
    private List<String> emails;

    @OneToOne(cascade = CascadeType.ALL) // Esto establece una relación uno a uno
    @JoinColumn(name = "metadata_id") // Columna de unión en la tabla de Documento
    private Metadata metadata;
}
