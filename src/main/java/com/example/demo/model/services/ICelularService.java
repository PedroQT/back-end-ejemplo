package com.example.demo.model.services;

import com.example.demo.model.entities.Celular;

import java.util.List;

public interface ICelularService {
    List<Celular> listar(); //devuelve una lista de objetos Celular
    Celular findById(Long id);
    void delete(Celular celular);
    Celular update(Celular celular);
    Celular save(Celular celular);
}
