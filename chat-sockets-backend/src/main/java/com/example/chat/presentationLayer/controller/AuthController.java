package com.example.chat.presentationLayer.controller;

import com.example.chat.businessLayer.service.auth.AuthService;
import com.example.chat.presentationLayer.dto.AuthResponseDTO;
import com.example.chat.presentationLayer.dto.UsuarioLoginDTO;
import com.example.chat.presentationLayer.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody UsuarioLoginDTO usuario
    ){

        try{
            AuthResponseDTO resultado = authService.iniciarSesion(usuario);

            log.info("Usuario {} inició sesión correctamente.", usuario.getEmail());

            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al iniciar sesión: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }
}
