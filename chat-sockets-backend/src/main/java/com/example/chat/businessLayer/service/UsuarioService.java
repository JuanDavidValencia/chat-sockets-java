package com.example.chat.businessLayer.service;

import com.example.chat.persistenceLayer.entity.Usuario;
import com.example.chat.persistenceLayer.entity.enums.EstadoUsuario;
import com.example.chat.presentationLayer.dto.*;

public interface UsuarioService {

    UsuarioResponseDTO registrarUsuario(UsuarioRegistroDTO usuario);

    UsuarioResponseDTO actualizarPerfil(UsuarioEditarDTO usuario);

    void cambiarPassword(UsuarioCambiarPasswordDTO password);

    void eliminarCuenta(Integer id);

    UsuarioResponseDTO cambiarEstado(Integer id, EstadoUsuario estado);

}
