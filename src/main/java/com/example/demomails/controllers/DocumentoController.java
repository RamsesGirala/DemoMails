package com.example.demomails.controllers;

import com.example.demomails.entities.Documento;
import com.example.demomails.entities.Metadata;
import com.example.demomails.repositories.DocumentoRepository;
import com.example.demomails.services.EmailService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DocumentoController {
    @Value("${spring.mail.success.message}")
    private String Mensaje_Exito;
    @Value("${spring.mail.error.message}")
    private String Mensaje_Error_Mail;
    @Value("${spring.mail.error.bd.message}")
    private String Mensaje_Error_BD;
    @Value("${spring.mail.subject}")
    private String subject;
    @Value("${spring.mail.message}")
    private String message;
    @Autowired
    DocumentoRepository documentoRepository;
    @Autowired
    EmailService emailService;

    @PostMapping("/civising/serviciofirma")
    public ResponseEntity<?> sendMail(@RequestParam String usuarioid, @RequestBody JsonNode json){
        try{
            //DESERIALIZAR INFORMACION
            Documento newDoc = getNewDoc(usuarioid,json);
            //PERSISTIR INFORMACION
            documentoRepository.save(newDoc);
            try{
                for(String mail : newDoc.getEmails()){
                    emailService.sendMailWithAttachment(mail,subject,message, newDoc.getDocumento(), newDoc.getDocname());
                }
                return ResponseEntity.status(HttpStatus.OK).body(Mensaje_Exito);
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mensaje_Error_Mail + e.getMessage());
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mensaje_Error_BD + e.getMessage());
        }
    }

    private Documento getNewDoc(String usuarioid, JsonNode json){
        Documento newDoc = new Documento();
        newDoc.setDocname(json.get("docname").asText());
        newDoc.setDocumento(json.get("documento").asText());
        newDoc.setUrl_callback(json.get("url_callback").asText());
        newDoc.setUsuarioid(usuarioid);

        // Definir formato en el que llega la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH.mm.ss");
        newDoc.setFecha(LocalDateTime.parse(json.get("fecha").asText(), formatter));
        //Guardar mails
        List<String> emails_list = new ArrayList<>();
        for (int i = 0; i < json.get("Lista_emails").size(); i++) {
            emails_list.add(json.get("Lista_emails").get(i).asText());
        }
        newDoc.setEmails(emails_list);
        //Guardar metadata
        Metadata newDocMetadata = new Metadata();
        newDocMetadata.setCargo(json.get("metadata").get("cargo").asText());
        newDocMetadata.setRazon(json.get("metadata").get("razon").asText());
        newDocMetadata.setLocalidad(json.get("metadata").get("localidad").asText());
        newDoc.setMetadata(newDocMetadata);
        return newDoc;
    }

}
