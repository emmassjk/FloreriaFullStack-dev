package com.vivitasol.projectbackend.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vivitasol.projectbackend.entities.Usuario;
import com.vivitasol.projectbackend.services.UsuarioServices;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioServices usuarioServices;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioServices.login(loginRequest.getCorreo(), loginRequest.getPassword());
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioServices.crear(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioServices.obtenerId(id);
        return ResponseEntity.ok(usuario);
    }

 
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioServices.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioServices.actualizar(id, usuarioActualizado);
        return ResponseEntity.ok(usuario);
    }

    static class LoginRequest {
        private String correo;
        private String password;

        // Getters and setters
        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
