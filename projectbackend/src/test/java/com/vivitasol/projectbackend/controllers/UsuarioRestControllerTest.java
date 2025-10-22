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
import com.vivitasol.projectbackend.entities.Role;
import com.vivitasol.projectbackend.entities.Usuario;
import com.vivitasol.projectbackend.services.StorageService;
import com.vivitasol.projectbackend.services.UsuarioServices;

@WebMvcTest(UsuarioRestController.class)
class UsuarioRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioServices usuarioServices;

    @MockBean
    private StorageService storageService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");
        usuario.setCorreo("test@gmail.com");
        usuario.setPassword("password");
        usuario.setRol(Role.USER);
    }

    @Test
    void testCrearUsuario() throws Exception {
        when(usuarioServices.crear(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test User"))
                .andExpect(jsonPath("$.correo").value("test@gmail.com"));
    }

    @Test
    void testLoginUsuario() throws Exception {
        when(usuarioServices.login(any(String.class), any(String.class))).thenReturn(usuario);

        String loginRequest = "{\"correo\":\"test@gmail.com\", \"password\":\"password\"}";

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("test@gmail.com"));
    }

    @Test
    void testObtenerUsuarioPorId() throws Exception {
        when(usuarioServices.obtenerId(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testListarUsuarios() throws Exception {
        List<Usuario> usuarios = Collections.singletonList(usuario);
        when(usuarioServices.listarTodos()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].correo").value("test@gmail.com"));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioServices).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActualizarUsuario() throws Exception {
        when(usuarioServices.actualizar(eq(1L), any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("test@gmail.com"));
    }
}
