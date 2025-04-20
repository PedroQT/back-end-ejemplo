package com.example.demo.model.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import jakarta.persistence.Id; // para usar la anotación @Id

@Entity // se usa para indicar que esta clase es una entidad
@Data //con lombok hace los getters y setters automaticamente con los constructores

public class Celular { // este proyecto gira entorno a una entidad, se llama celular y este es su metodo(tabla en la base de datos)
    @Id// se usa para identificar la clave primaria (anotacion de JPA)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // se usa para indicar que la clave primaria es autoincremental
    private Long id;       // atributos en la entidad celular
    private String marca;  // atributos en la entidad celular
    private String modelo; // atributos en la entidad celular
    private double precio; // atributos en la entidad celular
}