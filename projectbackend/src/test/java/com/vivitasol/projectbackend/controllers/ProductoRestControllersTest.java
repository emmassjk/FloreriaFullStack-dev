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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivitasol.projectbackend.entities.Categoria;
import com.vivitasol.projectbackend.entities.Producto;
import com.vivitasol.projectbackend.services.CategoriaServices;
import com.vivitasol.projectbackend.services.ProductoServices;
import com.vivitasol.projectbackend.services.StorageService;

@WebMvcTest(ProductoRestControllers.class)
class ProductoRestControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoServices productoServices;

    @MockBean
    private StorageService storageService;

    @MockBean
    private CategoriaServices categoriaServices;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Ramos");

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Ramo de Rosas");
        producto.setCategoria(categoria);
        producto.setActivo(true);
    }

    @Test
    void testCrearProducto() throws Exception {
    MockMultipartFile imagen = new MockMultipartFile("imagen", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes());

        when(categoriaServices.obtenerId(1L)).thenReturn(categoria);
        when(storageService.store(any())).thenReturn("test.jpg");
        when(productoServices.crear(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(multipart("/api/productos")
                .file(imagen)
                .param("nombre", "Ramo de Rosas")
                .param("descripcion", "Ramo de 12 rosas rojas")
                .param("precio", "500")
                .param("stock", "10")            
                .param("activo", "true")          
                .param("categoriaId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ramo de Rosas"));
    }

    @Test
    void testObtenerProductoPorId() throws Exception {
        when(productoServices.obtenerId(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testListarProductos() throws Exception {
        List<Producto> productos = Collections.singletonList(producto);
        when(productoServices.listarTodas()).thenReturn(productos);

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Ramo de Rosas"));
    }

    @Test
    void testEliminarProducto() throws Exception {
        doNothing().when(productoServices).eliminar(1L);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActualizarProducto() throws Exception {
        when(productoServices.actualizar(eq(1L), any(Producto.class))).thenReturn(producto);

        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ramo de Rosas"));
    }

    @Test
    void testDesactivarProducto() throws Exception {
        when(productoServices.desactivar(1L)).thenReturn(producto);

        mockMvc.perform(patch("/api/productos/1/desactivar"))
                .andExpect(status().isOk());
    }
}
