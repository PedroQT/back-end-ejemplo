package com.example.demo.model.services;

import com.example.demo.model.dao.CelularDao;
import com.example.demo.model.entities.Celular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // indica que esta clase es un servicio, es un componente de negocio
public class CelularServiceImpl implements ICelularService { // este metodo implementa la interfaz IPaisService

    @Autowired // Inyeccion de dependencias, se inyecta el dao para poder usarlo
    CelularDao celularDao;

//---------Este metodo se encarga de listar todos los celulares, retorna una lista de celulares------------------
// Método que sobrescribe un método de la interfaz (IPaisService, en este caso).

    @Override // indica que este metodo sobrescribe un metodo de la interfaz
    public List<Celular> listar() { //listar() retorna una lista de objetos Celular
        return (List<Celular>) celularDao.findAll(); // llama al metodo findAll() del dao, que devuelve todos los objetos de la tabla celular
    }

    @Override
    public Celular save(Celular celular) {
        return celularDao.save(celular);
    }

    @Override
    public  Celular update(Celular celular) {
        return celularDao.save(celular);
    }

    @Override
    public Celular findById(Long id) {
        return celularDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Celular celular) { celularDao.delete(celular);
    }


}
