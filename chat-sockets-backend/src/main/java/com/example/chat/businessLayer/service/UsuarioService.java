package com.example.chat.businessLayer.service;

import com.example.chat.persistenceLayer.entity.Usuario;
import com.example.chat.persistenceLayer.entity.enums.EstadoUsuario;

public interface UsuarioService {

    Usuario registrarUsuario(Usuario usuario);

    Usuario iniciarSesion(String email, String password);

    Usuario actualizarPerfil(Integer id, Usuario usuario);

    void eliminarCuenta(Integer id);

    Usuario cambiarEstado(Integer id, EstadoUsuario estado);

}
