package com.example.demo;

import com.example.demo.model.entities.Celular;
import com.example.demo.model.services.ICelularService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias (unit tests) para la API RESTful que se encarga de realizar operaciones CRUD sobre una entidad
 * llamada "Celular".
 * Se importan las clases necesarias para realizar las pruebas (MockMvc, ObjectMapper, etc.), se inyecta el servicio
 * que se encarga de realizar las operaciones sobre la entidad "Celular" (ICelularService), y se definen varios métodos de
 * prueba para la API RESTful, que comprueban el correcto funcionamiento de los métodos:
 * GET, POST, PUT y DELETE de la API.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CelularRestControllerTest {
    /**
     * Esta anotación @Autowired permite la inyección de dependencia, lo que significa que
     * wac (WebApplicationContext) se inicializará automáticamente con el contexto de la aplicación web.
     * El WebApplicationContext proporciona acceso a los componentes y configuraciones de Spring.
     */
    @Autowired
    private WebApplicationContext wac;

    /**
     * MockMvc es una clase que se utiliza para simular las solicitudes HTTP y probar
     * controladores y endpoints sin necesidad de un navegador web en una prueba de integración.
     */
    private MockMvc mockMvc;

    @Autowired
    private ICelularService celularService;

    /**
     * Inicializa los objetos necesarios para la prueba. En el ejemplo de código dado, este método se utiliza para
     * inicializar el objeto MockMvc, que se utiliza para simular el envío de solicitudes HTTP en la prueba de la
     * clase CelularRestController.
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Prueba del método GET "/api/v1/demo/hola/{nombre}", que comprueba que se recibe el nombre correcto
     * en la respuesta.
     * @throws Exception Se lanza una excepción si no devuelve el mensaje correcto.
     */
    @Test
    public void testHolaMundo() throws Exception {
        String nombre = "Pedro";
        this.mockMvc.perform(get("/api/v1/demo/hola/{nombre}", nombre))
                .andExpect(status().isOk())
                .andExpect(content().string("Hola " + nombre));
    }

    /**
     * Prueba del método GET "/api/v1/demo/celulares", que comprueba que se recibe una lista de los celulares en la respuesta.
     * @throws Exception Se lanza una excepción si no devuelve la lista de los celulares correcta.
     */
    @Test
    public void testListar() throws Exception {
        Celular celular1 = new Celular(null, "Samsung", "Galaxy S22", 3000.0);
        Celular celular2 = new Celular(null, "Apple", "iPhone 13", 3499.99);
        celularService.save(celular1);
        celularService.save(celular2);

        this.mockMvc.perform(get("/api/v1/demo/celulares"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].celular", is(celular1.getMarca())))
                .andExpect(jsonPath("$[0].celular", is(celular2.getMarca())));

        celularService.delete(celular1);
        celularService.delete(celular2);
    }

    /**
     * Prueba del método GET "/api/v1/demo/celulares/{id}", que comprueba que se recibe el celular correcto en la respuesta.
     * @throws Exception Se lanza una excepción si no se encuentra el celular con el id especificado.
     */
    @Test
    public void testBuscarCelular() throws Exception {
        Celular celular = new Celular(null, "Xiaomi", "Redmi Note 13", 1200.0);
        celular = celularService.save(celular);

        this.mockMvc.perform(get("/api/v1/demo/celulares/{id}", celular.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.celular", is(celular.getMarca())))
                .andExpect(jsonPath("$.celular", is(celular.getModelo())));

        celularService.delete(celular);
    }

    /**
     * Prueba del método POST "/api/v1/demo/celulares", que comprueba que se crea un nuevo celular correctamente.
     * @throws Exception Se lanza una excepción si no se encuentra el celular con el id especificado.
     */
    @Test
    public void testCrearCelular() throws Exception {
        Celular celular = new Celular(null, "Motorola", "G82", 899.99);

        this.mockMvc.perform(post("/api/v1/demo/celulares")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(celular)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.celular", is(celular.getMarca())));

        celularService.delete(celular);
    }

    /**
     * Prueba del método PUT "/api/v1/demo/celulares", que comprueba que se actualiza un celular correctamente.
     * @throws Exception Se lanza una excepción si no se encuentra el celular con el id especificado.
     */
    @Test
    public void testActualizarCelular() throws Exception {
        Celular celular = new Celular(null, "Realme", "Narzo 30", 799.99);
        celular = celularService.save(celular);
        celular.setMarca("Realme Modificado");

        this.mockMvc.perform(put("/api/v1/demo/celulares/{id}", celular.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(celular)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.celular", is(celular.getMarca())));

        celularService.delete(celular);
    }

    /**
     * Prueba del método DELETE "/api/v1/demo/celulares/{id}", que comprueba que se elimina un celular correctamente.
     * @throws Exception Se lanza una excepción si no se encuentra el celular con el id especificado.
     */
    @Test
    public void testBorrarCelular() throws Exception {
        Celular celular = new Celular(null, "Oppo", "Find X5", 3999.99);
        celular = celularService.save(celular);

        this.mockMvc.perform(delete("/api/v1/demo/celulares/{id}", celular.getId()))
                .andExpect(status().isOk());

        assertNull(celularService.findById(celular.getId()));
    }

    /**
     * Método para convertir un objeto a una cadena JSON
     *
     * @param obj Objeto a convertir
     * @return Cadena JSON
     */
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
