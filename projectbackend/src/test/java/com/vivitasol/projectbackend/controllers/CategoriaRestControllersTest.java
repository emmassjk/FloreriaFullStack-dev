package com.vivitasol.projectbackend.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivitasol.projectbackend.entities.Categoria;
import com.vivitasol.projectbackend.services.CategoriaServices;
import com.vivitasol.projectbackend.services.StorageService;

@WebMvcTest(CategoriaRestControllers.class)
class CategoriaRestControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaServices categoriaServices;

    @MockBean
    private StorageService storageService;

    @Autowired
    private ObjectMapper objectMapper;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Ramos");
        categoria.setDescripcion("Arreglos florales en ramo");
    }

    @Test
    void testCrearCategoria() throws Exception {
        when(categoriaServices.crear(any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ramos"));
    }

    @Test
    void testObtenerCategoriaPorId() throws Exception {
        when(categoriaServices.obtenerId(1L)).thenReturn(categoria);

        mockMvc.perform(get("/api/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testListarCategorias() throws Exception {
        List<Categoria> categorias = Collections.singletonList(categoria);
        when(categoriaServices.listarTodas()).thenReturn(categorias);

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Ramos"));
    }

    @Test
    void testEliminarCategoria() throws Exception {
        doNothing().when(categoriaServices).eliminar(1L);

        mockMvc.perform(delete("/api/categorias/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActualizarCategoria() throws Exception {
        when(categoriaServices.actualizar(eq(1L), any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(put("/api/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ramos"));
    }
}
