package com.vivitasol.projectbackend.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivitasol.projectbackend.entities.Usuario;
import com.vivitasol.projectbackend.repositories.UsuarioRepositories;

@Service
public class UsuarioServicesImpl implements UsuarioServices {

    @Autowired
    private UsuarioRepositories usuarioRepositories;

    @Override
    public Usuario crear(Usuario usuario){
        return usuarioRepositories.save(usuario);
    }


    @Override
    public Usuario obtenerId(Long id) {
        return usuarioRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public List<Usuario> listarTodos() {
        return (List<Usuario>) usuarioRepositories.findAll();
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepositories.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepositories.deleteById(id);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        Usuario existente = obtenerId(id);
        existente.setNombre(usuarioActualizado.getNombre());
        existente.setCorreo(usuarioActualizado.getCorreo());
        existente.setPassword(usuarioActualizado.getPassword());
        existente.setRol(usuarioActualizado.getRol());
        return usuarioRepositories.save(existente);
    }

    @Override
    public Usuario login(String correo, String password) {
        Optional<Usuario> usuarioOptional = usuarioRepositories.findByCorreo(correo);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        throw new RuntimeException("Correo o contraseña incorrectos");
    }


}
