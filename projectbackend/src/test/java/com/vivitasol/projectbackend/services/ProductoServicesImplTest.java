package com.vivitasol.projectbackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vivitasol.projectbackend.entities.Producto;
import com.vivitasol.projectbackend.repositories.ProductoRepositories;

class ProductoServicesImplTest {

    @Mock
    private ProductoRepositories productoRepositories;

    @InjectMocks
    private ProductoServicesImpl productoServices;

    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Ramo de Rosas");
        producto.setDescripcion("Ramo de 12 rosas rojas");
        producto.setPrecio(500L);
        producto.setActivo(true);
    }

    @Test
    void testCrearProducto() {
        when(productoRepositories.save(any(Producto.class))).thenReturn(producto);
        Producto nuevoProducto = productoServices.crear(new Producto());
        assertNotNull(nuevoProducto);
        assertEquals(producto.getNombre(), nuevoProducto.getNombre());
    }

    @Test
    void testListarTodosLosProductos() {
        when(productoRepositories.findAll()).thenReturn(Collections.singletonList(producto));
        List<Producto> productos = productoServices.listarTodas();
        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
    }

    @Test
    void testObtenerProductoPorId() {
        when(productoRepositories.findById(1L)).thenReturn(Optional.of(producto));
        Producto encontrado = productoServices.obtenerId(1L);
        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
    }

    @Test
    void testObtenerProductoPorIdNoEncontrado() {
        when(productoRepositories.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            productoServices.obtenerId(1L);
        });
    }

    @Test
    void testActualizarProducto() {
        when(productoRepositories.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepositories.save(any(Producto.class))).thenReturn(producto);

        Producto actualizado = new Producto();
        actualizado.setDescripcion("Ramo de 24 rosas rojas");
        actualizado.setPrecio(900L);

        Producto resultado = productoServices.actualizar(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Ramo de 24 rosas rojas", resultado.getDescripcion());
        assertEquals(900L, resultado.getPrecio());
    }

    @Test
    void testEliminarProducto() {
        when(productoRepositories.existsById(1L)).thenReturn(true);
        doNothing().when(productoRepositories).deleteById(1L);
        productoServices.eliminar(1L);
        verify(productoRepositories, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarProductoNoEncontrado() {
        when(productoRepositories.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> {
            productoServices.eliminar(1L);
        });
    }

    @Test
    void testDesactivarProducto() {
        when(productoRepositories.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepositories.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoServices.desactivar(1L);

        assertNotNull(resultado);
        assertFalse(resultado.getActivo());
    }
}
