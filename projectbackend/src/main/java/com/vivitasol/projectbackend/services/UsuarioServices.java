package com.vivitasol.projectbackend.services;

import com.vivitasol.projectbackend.entities.Usuario;
import java.util.List;

public interface UsuarioServices {

    Usuario crear(Usuario usuario);
    Usuario obtenerId(Long id);
    List<Usuario> listarTodos();    
    void eliminar(Long id);
    Usuario actualizar(Long id, Usuario usuarioActualizado);
    Usuario login(String correo, String password);

}
