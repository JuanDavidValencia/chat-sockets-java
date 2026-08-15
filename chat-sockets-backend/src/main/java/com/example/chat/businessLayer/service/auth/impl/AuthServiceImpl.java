package com.example.chat.businessLayer.service.auth.impl;

import com.example.chat.businessLayer.service.auth.AuthService;
import com.example.chat.persistenceLayer.entity.Usuario;
import com.example.chat.persistenceLayer.repository.UsuarioRepository;
import com.example.chat.presentationLayer.dto.AuthResponseDTO;
import com.example.chat.presentationLayer.dto.UsuarioLoginDTO;
import com.example.chat.presentationLayer.dto.UsuarioResponseDTO;
import com.example.chat.securityLayer.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponseDTO iniciarSesion(UsuarioLoginDTO usuario){
        Usuario usuarioEntidad = usuarioRepository.findByEmail(usuario.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Correo o contraseña incorrectos."));


        validarCredenciales(usuario, usuarioEntidad);

        String token = jwtService.generarToken(usuarioEntidad.getIdUsuario());

        return new AuthResponseDTO(token);
    }

    private void validarCredenciales(UsuarioLoginDTO usuario, Usuario response) {

        if (!passwordEncoder.matches(usuario.getPassword(), response.getPassword())) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos.");
        }

    }




}
