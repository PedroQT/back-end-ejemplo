package com.example.demo.controller;


import com.example.demo.model.entities.Celular;
import com.example.demo.model.services.CelularServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins =  "*") //-----> se permiten clientes que se conecten al localhost
@RestController   // identifica esta clase como un restcontroller
@RequestMapping("/api/v1/demo") // activa este controlador usando el end-point definido

public class CelularRestController { // declaracion de la clase rest controller
    @Autowired
    private CelularServiceImpl celularService; // inyeccion que se hace de celular sevice

//--------------END-POINT GET QUE RECIBE UN NOMBRE COMO PARAMETRO EN LA URL Y DEVUELVE UN SALUDO (METODO)-----------------------------------

    @GetMapping("/hola/{nombre}") // mapea solicitudes http get a /hola/{nombre} (end-point)
    public String holaMundo(@PathVariable("nombre") String nombre) { //Extrae el valor {nombre} de la URL.
        return "Hola " + nombre; // retorna el mensaje hola con la variable nombre
    }

    //---------------END-POINT GET PARA LISTAR LOS CELULARES (METODO).--------------------------------------------------------------------------

    @GetMapping("/celulares") // mapea solicitudes http get a /celulares (end-point)
    public ResponseEntity<?> listar() { //metodo de listar
        try {
            List<Celular> celulares = this.celularService.listar(); // intenta obtener todos los celulares mediante el service
            return ResponseEntity.ok(celulares); // si tiene exito devuelve un HTTP 200
        } catch (Exception e) { //este es un manejor de errores, si no lo puede hacer devuelve un http500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar las celulares: " + e.getMessage());
        }
    }

    //----------------END_POINT POST PARA GUARDAR LOS CELULARES (METODO)------------------------------------------------------------------------

    @PostMapping("/celulares") // mapea solicitudes http post a /celulares (end-point)
    public ResponseEntity<?> guardarCelular(@RequestBody Celular celular) { //metodo de guardar
        try {
            Celular celulares = this.celularService.save(celular); // intenta guardar todos los datos de la entidad celular mediante el service
            return ResponseEntity.ok(celulares); // si es exitoso devuelve un HTTP 200
        } catch (Exception e) { // manejo de errores, si no funciona retorna un HTTP 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la celular: " + e.getMessage());
        }
    }

    //----------------END-POINT GET PARA BUSCAR UN CELULAR POR EL ID (METODO)---------------------------------------------------------------------------

    @GetMapping("/celulares/{id}") // mapea solicitudes http get a /celulares/{id} (end-point)
    public ResponseEntity<Celular> buscarCelular(@PathVariable Long id) { // metodo de buscar por id
        try {
            Celular celular = celularService.findById(id); // intenta buscar la entidad celular que corresponte a ese id mediante el service
            return ResponseEntity.ok(celular); // si es exitisa la busqueda, retorna un HTTP200
        } catch (Exception e) { // manejor de errores, retorna un HTTP500
            // error o devolver un mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //------------------END-POINT DELETER PARA ELIMINAR UNA ENTIDAD POR SU ID (METODO)--------------------------------------------------------------------------

    @DeleteMapping("/celulares/{id}") // mapea las solicitudes http delete a /celulares/{id} (end-point)
    public ResponseEntity<?> eliminarCelular(@PathVariable Long id) { //metodo para eliminar entidad
        Map<String, Object> response = new HashMap<>(); // es para construir respuestas estructuradas en JSON, es para enviar mensajes de éxito/error al cliente
        Celular celulares = null; // se le asigna al objeto celulares un valor null(vacio)
        try {
            celulares = this.celularService.findById(id); // intenta buscar el celular con el id ingresado mediante el service
            if (celulares == null) { // si no escontro nada
                response.put("mensaje", "La entidad con ID " + id + " no existe."); //Agrega mensaje descriptivo al Map response
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //Retorna HTTP404 con el mensaje
            }
            // Si existe
            this.celularService.delete(celulares); //Ejecuta el métod delete del servicio para eliminarlo
            response.put("mensaje", "La entidad con ID " + id + " ha sido eliminada con éxito."); //Agrega mensaje descriptivo al Map response
            return new ResponseEntity<>(response, HttpStatus.OK); //retorna HTTP200 con el mensaje

        } catch (Exception e) { // manejo de errores, Agrega mensaje descriptivo al Map response y retorna un HTTP500w
            response.put("mensaje", "Error al buscar la entidad.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    // --------------END-POINT PUT PARA ACTUALIZAR LA ENTIDAD QUE CORRESPONDA AL ID (METODO)--------------------------------------------------------------------

    @PutMapping("/celulares/{id}") // mapeo de solicitudes http put a /celulares/{id} (end-point)
    public ResponseEntity<?> actualizarCelular(@RequestBody Celular celular) { // metodo para catualizar
        try {
            Celular celulares = this.celularService.update(celular); //intenta actualizar i guardar la entidad que corresponte al id
            return ResponseEntity.ok(celulares); // si es exitoso, devuelve un HTTP200
        } catch (Exception e) { // manejo de errores, devuelve un HTTP500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la entidad: " + e.getMessage());
        }
    }
}
