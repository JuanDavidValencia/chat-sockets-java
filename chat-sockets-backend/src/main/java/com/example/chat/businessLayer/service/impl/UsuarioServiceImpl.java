package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.service.UsuarioService;
import com.example.chat.persistenceLayer.entity.Usuario;
import com.example.chat.persistenceLayer.repository.UsuarioRepository;
import com.example.chat.presentationLayer.dto.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.chat.persistenceLayer.entity.enums.EstadoUsuario;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO registrarUsuario(UsuarioRegistroDTO usuario){
        log.info("Creando el usuario");

        validarInformacion(usuario);

        Usuario usuarioEntidad = new Usuario();

        if (usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new IllegalArgumentException("Ya existe registrado con ese correo");
        }

        usuarioEntidad.setEstado(EstadoUsuario.ACTIVO);
        usuarioEntidad.setFechaRegistro(LocalDate.now());
        usuarioEntidad.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioEntidad.setTelefono(usuario.getTelefono());
        usuarioEntidad.setEmail(usuario.getEmail());
        usuarioEntidad.setNombre(usuario.getNombre());

        usuarioRepository.save(usuarioEntidad);

        return new UsuarioResponseDTO(
                usuarioEntidad.getIdUsuario(),
                usuarioEntidad.getNombre(),
                usuarioEntidad.getTelefono(),
                usuarioEntidad.getEmail(),
                usuarioEntidad.getEstado(),
                usuarioEntidad.getFechaRegistro()
        );


    }

    @Override
    public UsuarioResponseDTO actualizarPerfil(UsuarioEditarDTO usuario){
        Usuario usuarioEntidad = obtenerUsuarioPorId(usuario.getIdUsuario());

        if (usuarioRepository.existsByEmailAndIdUsuarioNot(
                usuario.getEmail(),
                usuario.getIdUsuario()
        )){
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        actualizarDatosPerfil(usuarioEntidad, usuario);

        usuarioRepository.save(usuarioEntidad);

        return new UsuarioResponseDTO(
                usuarioEntidad.getIdUsuario(),
                usuarioEntidad.getNombre(),
                usuarioEntidad.getTelefono(),
                usuarioEntidad.getEmail(),
                usuarioEntidad.getEstado(),
                usuarioEntidad.getFechaRegistro()
        );
    }

    @Override
    public void cambiarPassword(UsuarioCambiarPasswordDTO password) {



    }

    @Override
    public void eliminarCuenta(Integer id){
        Usuario usuario = obtenerUsuarioPorId(id);

        if (usuario.getEstado().equals(EstadoUsuario.INACTIVO)) {
            return;
        }

        usuario.setEstado(EstadoUsuario.INACTIVO);

        usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioResponseDTO cambiarEstado(Integer id, EstadoUsuario estado){
        Usuario usuario = obtenerUsuarioPorId(id);

        if (estado == null){
            throw new IllegalArgumentException("El estado es obligatorio");
        }

        usuario.setEstado(estado);

        usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getTelefono(),
                usuario.getEmail(),
                usuario.getEstado(),
                usuario.getFechaRegistro()
        );
    }

    private void validarInformacion(UsuarioRegistroDTO usuario){

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre del usuario es obligatorio");
        }

        if (usuario.getNombre().length() > 150){
            throw new IllegalArgumentException("El nombre del usuario no puede ser de más de 150 caracteres");
        }

        if (usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()){
            throw new IllegalArgumentException("El teléfono deber ser obligatorio");
        }

        if (usuario.getEmail() == null  || usuario.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("El email del usuario es obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty() || usuario.getPassword().length() < 8){
            throw new IllegalArgumentException("La contraseña del usuario debe tener al menos 8 caracteres");
        }

    }

    private Usuario obtenerUsuarioPorId(Integer id){
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El usuario no está registrado"
                        ));
    }

    private void actualizarDatosPerfil(Usuario existente, UsuarioEditarDTO nuevo){
        existente.setNombre(nuevo.getNombre());
        existente.setTelefono(nuevo.getTelefono());
        existente.setEmail(nuevo.getEmail());
    }


}
