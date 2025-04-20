package com.example.demo.model.dao;

import com.example.demo.model.entities.Celular;
import org.springframework.data.repository.CrudRepository;


// DAO: Data Access Object: maneja las operaciones CRUD de la entidad
// las interfaces no tienen codigo, solo definen los metodos
// CrudRepository: interfaz de Spring que proporciona los metodos CRUD

// el crud para la entidad celular y Long es el tipo de variable de la llave primaria
public interface CelularDao extends CrudRepository<Celular, Long> {
}
