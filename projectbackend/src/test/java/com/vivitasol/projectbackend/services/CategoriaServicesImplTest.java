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

import com.vivitasol.projectbackend.entities.Categoria;
import com.vivitasol.projectbackend.repositories.CategoriaRepositories;

class CategoriaServicesImplTest {

    @Mock
    private CategoriaRepositories categoriaRepositories;

    @InjectMocks
    private CategoriaServicesImpl categoriaServices;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Ramos");
        categoria.setDescripcion("Arreglos florales en ramo");
    }

    @Test
    void testCrearCategoria() {
        when(categoriaRepositories.save(any(Categoria.class))).thenReturn(categoria);
        Categoria nuevaCategoria = categoriaServices.crear(new Categoria());
        assertNotNull(nuevaCategoria);
        assertEquals(categoria.getNombre(), nuevaCategoria.getNombre());
    }

    @Test
    void testListarTodasLasCategorias() {
        when(categoriaRepositories.findAll()).thenReturn(Collections.singletonList(categoria));
        List<Categoria> categorias = categoriaServices.listarTodas();
        assertFalse(categorias.isEmpty());
        assertEquals(1, categorias.size());
    }

    @Test
    void testObtenerCategoriaPorId() {
        when(categoriaRepositories.findById(1L)).thenReturn(Optional.of(categoria));
        Categoria encontrada = categoriaServices.obtenerId(1L);
        assertNotNull(encontrada);
        assertEquals(1L, encontrada.getId());
    }

    @Test
    void testObtenerCategoriaPorIdNoEncontrada() {
        when(categoriaRepositories.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            categoriaServices.obtenerId(1L);
        });
    }

    @Test
    void testActualizarCategoria() {
        when(categoriaRepositories.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepositories.save(any(Categoria.class))).thenReturn(categoria);

        Categoria actualizada = new Categoria();
        actualizada.setNombre("Ramos Actualizados");

        Categoria resultado = categoriaServices.actualizar(1L, actualizada);

        assertNotNull(resultado);
        assertEquals("Ramos Actualizados", resultado.getNombre());
    }

    @Test
    void testEliminarCategoria() {
        when(categoriaRepositories.existsById(1L)).thenReturn(true);
        doNothing().when(categoriaRepositories).deleteById(1L);
        categoriaServices.eliminar(1L);
        verify(categoriaRepositories, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarCategoriaNoEncontrada() {
        when(categoriaRepositories.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> {
            categoriaServices.eliminar(1L);
        });
    }
}
