package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.service.UsuarioService;
import com.example.chat.persistenceLayer.repository.UsuarioRepository;
import com.example.chat.persistenceLayer.entity.Usuario;
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
    public Usuario registrarUsuario(Usuario usuario){
        log.info("Creando el usuario");

        validarInformacion(usuario);

        if (usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new IllegalArgumentException("Ya existe registrado con ese correo");
        }

        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);


    }

    @Override
    public Usuario iniciarSesion(String email, String password){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Correo o contraseña incorrectos."));


        validarCredenciales(password, usuario);

        return usuario;
    }

    @Override
    public Usuario actualizarPerfil(Integer id, Usuario usuario){
        Usuario usuarioExistente = obtenerUsuarioPorId(id);

        actualizarDatosPerfil(usuarioExistente, usuario);

        return usuarioRepository.save(usuarioExistente);
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
    public Usuario cambiarEstado(Integer id, EstadoUsuario estado){
        Usuario usuario = obtenerUsuarioPorId(id);

        if (estado == null){
            throw new IllegalArgumentException("El estado es obligatorio");
        }

        usuario.setEstado(estado);

        return usuarioRepository.save(usuario);
    }

    private void validarInformacion(Usuario usuario){

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

    private void validarCredenciales(String password, Usuario response){

        if (!passwordEncoder.matches(password, response.getPassword())){
            throw new IllegalArgumentException("Correo o contraseña incorrectos.");
        }

    }

    private void actualizarDatosPerfil(Usuario existente, Usuario nuevo){
        existente.setNombre(nuevo.getNombre());
        existente.setTelefono(nuevo.getTelefono());
        existente.setEmail(nuevo.getEmail());
        existente.setEstado(nuevo.getEstado());
    }



}
