package com.example.chat.businessLayer.service.auth;

import com.example.chat.presentationLayer.dto.AuthResponseDTO;
import com.example.chat.presentationLayer.dto.UsuarioLoginDTO;
import com.example.chat.presentationLayer.dto.UsuarioResponseDTO;

public interface AuthService {

    AuthResponseDTO iniciarSesion(UsuarioLoginDTO usuario);

}
