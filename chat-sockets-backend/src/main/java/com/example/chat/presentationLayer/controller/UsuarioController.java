package com.example.chat.presentationLayer.controller;

import com.example.chat.businessLayer.service.UsuarioService;
import com.example.chat.businessLayer.service.auth.AuthService;
import com.example.chat.persistenceLayer.entity.enums.EstadoUsuario;
import com.example.chat.presentationLayer.dto.UsuarioEditarDTO;
import com.example.chat.presentationLayer.dto.UsuarioLoginDTO;
import com.example.chat.presentationLayer.dto.UsuarioRegistroDTO;
import com.example.chat.presentationLayer.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(
            @RequestBody UsuarioRegistroDTO usuario
    ){
        try{

            UsuarioResponseDTO resultado = usuarioService.registrarUsuario(usuario);
            log.info("Usuario {} registrado correctamente.", usuario.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);

        }catch (IllegalArgumentException e){
            log.warn("Error de validación al crear usuario: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @RequestBody UsuarioEditarDTO usuario
    ){

        try{

            UsuarioResponseDTO resultado = usuarioService.actualizarPerfil(usuario);
            log.info("Usuario {} fue modificado correctamente.", resultado.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(resultado);

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al actualizar el usuario: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id
    ){
        try{

            usuarioService.eliminarCuenta(id);
            log.info("Usuario con id {} eliminado correctamente.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al eliminar el usuario: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<UsuarioResponseDTO> cambiarEstado(
            @PathVariable Integer id,
            @RequestBody EstadoUsuario estado
    ) {

        try{

            UsuarioResponseDTO usuario = usuarioService.cambiarEstado(id, estado);
            log.info("Usuario {} pasó a estado {}.", usuario.getEmail(), estado);
            return ResponseEntity.status(HttpStatus.OK).body(usuario);

        } catch (IllegalArgumentException e){

            log.warn("Error de validación al cambiar el estado del usuario: {}.", e.getMessage());
            return ResponseEntity.badRequest().build();

        }

    }




}
