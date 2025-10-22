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

import com.vivitasol.projectbackend.entities.Role;
import com.vivitasol.projectbackend.entities.Usuario;
import com.vivitasol.projectbackend.repositories.UsuarioRepositories;

class UsuarioServicesImplTest {

    @Mock
    private UsuarioRepositories usuarioRepositories;

    @InjectMocks
    private UsuarioServicesImpl usuarioServices;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCorreo("test@gmail.com");
        usuario.setPassword("password");
        usuario.setRol(Role.USER);
    }

    @Test
    void testCrearUsuario() {
        when(usuarioRepositories.save(any(Usuario.class))).thenReturn(usuario);
        Usuario nuevoUsuario = usuarioServices.crear(new Usuario());
        assertNotNull(nuevoUsuario);
        assertEquals(usuario.getCorreo(), nuevoUsuario.getCorreo());
    }

    @Test
    void testListarTodosLosUsuarios() {
        when(usuarioRepositories.findAll()).thenReturn(Collections.singletonList(usuario));
        List<Usuario> usuarios = usuarioServices.listarTodos();
        assertFalse(usuarios.isEmpty());
        assertEquals(1, usuarios.size());
    }

    @Test
    void testObtenerUsuarioPorId() {
        when(usuarioRepositories.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario encontrado = usuarioServices.obtenerId(1L);
        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
    }

    @Test
    void testObtenerUsuarioPorIdNoEncontrado() {
        when(usuarioRepositories.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            usuarioServices.obtenerId(1L);
        });
    }

    @Test
    void testActualizarUsuario() {
        when(usuarioRepositories.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepositories.save(any(Usuario.class))).thenReturn(usuario);

        Usuario actualizado = new Usuario();
        actualizado.setCorreo("actualizado@gmail.com");
        actualizado.setPassword("newpassword");
        actualizado.setRol(Role.ADMIN);

        Usuario resultado = usuarioServices.actualizar(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Updated Name", resultado.getNombre());
        assertEquals("actualizado@gmail.com", resultado.getCorreo());
        assertEquals(Role.ADMIN, resultado.getRol());
    }

    @Test
    void testEliminarUsuario() {
        when(usuarioRepositories.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepositories).deleteById(1L);
        usuarioServices.eliminar(1L);
        verify(usuarioRepositories, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarUsuarioNoEncontrado() {
        when(usuarioRepositories.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> {
            usuarioServices.eliminar(1L);
        });
    }

    @Test
    void testLogin_Success() {
        when(usuarioRepositories.findByCorreo("test@gmail.com")).thenReturn(Optional.of(usuario));
        Usuario result = usuarioServices.login("test@gmail.com", "password");
        assertNotNull(result);
        assertEquals("test@gmail.com", result.getCorreo());
    }

    @Test
    void testLogin_WrongPassword() {
        when(usuarioRepositories.findByCorreo("test@gmail.com")).thenReturn(Optional.of(usuario));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioServices.login("test@gmail.com", "wrongpassword");
        });
        assertEquals("Correo o contraseña incorrectos", exception.getMessage());
    }

    @Test
    void testLogin_UserNotFound() {
        when(usuarioRepositories.findByCorreo("notfound@gmail.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioServices.login("notfound@gmail.com", "password");
        });
        assertEquals("Correo o contraseña incorrectos", exception.getMessage());
    }
}
