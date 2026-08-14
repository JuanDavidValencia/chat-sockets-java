package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.service.ContactoService;
import com.example.chat.persistenceLayer.entity.Contacto;
import com.example.chat.persistenceLayer.entity.Usuario;
import com.example.chat.persistenceLayer.repository.ContactoRepository;
import com.example.chat.persistenceLayer.repository.UsuarioRepository;
import com.example.chat.presentationLayer.dto.ContactoEditarDTO;
import com.example.chat.presentationLayer.dto.ContactoRequestDTO;
import com.example.chat.presentationLayer.dto.ContactoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactoServiceImpl implements ContactoService {

    private final ContactoRepository contactoRepository;

    private final UsuarioRepository usuarioRepository;


    @Override
    public ContactoResponseDTO crearContacto(ContactoRequestDTO contacto) {

        log.info("Creando contacto...");

        Contacto contactoEntidad = new Contacto();

        Usuario usuarioCreador = usuarioRepository.findById(contacto.getIdUsuario())
                .orElseThrow(() ->
                        new IllegalArgumentException("El creador no existe.")
                );

        Usuario usuarioContacto = usuarioRepository.findById(contacto.getIdUsuarioContacto())
                        .orElseThrow(() ->
                                new IllegalArgumentException("El usuario de contacto no existe."))
                ;

        contactoEntidad.setUsuario(usuarioCreador);
        contactoEntidad.setContacto(usuarioContacto);
        contactoEntidad.setNombrePersonalizado(contacto.getNombrePersonalizado());
        contactoEntidad.setFechaCreacion(LocalDate.now());

        Contacto contactoGuardado = contactoRepository.save(contactoEntidad);

        return new ContactoResponseDTO(
                contactoGuardado.getIdContacto(),
                contactoGuardado.getContacto().getIdUsuario(),
                contactoGuardado.getNombrePersonalizado(),
                contactoGuardado.getFechaCreacion()
        );
    }

    @Override
    public ContactoResponseDTO buscarContacto(Integer idContacto) {
        log.info("Buscando contacto...");

        Contacto contacto = contactoRepository.findById(idContacto)
                .orElseThrow(() ->
                        new IllegalArgumentException("El contacto no existe.")
                );

        return new ContactoResponseDTO(
                contacto.getIdContacto(),
                contacto.getContacto().getIdUsuario(),
                contacto.getNombrePersonalizado(),
                contacto.getFechaCreacion()
        );

    }

    @Override
    public ContactoResponseDTO editarContacto(ContactoEditarDTO contacto) {
        log.info("Editando contacto...");

        Contacto contactoEditar = contactoRepository.findById(contacto.getIdContacto())
                .orElseThrow(() ->
                        new IllegalArgumentException("El contacto no existe.")
                );

        contactoEditar.setNombrePersonalizado(contacto.getNombrePersonalizado());

        Contacto contactoGuardado = contactoRepository.save(contactoEditar);

        return new ContactoResponseDTO(
                contactoGuardado.getIdContacto(),
                contactoGuardado.getContacto().getIdUsuario(),
                contactoGuardado.getNombrePersonalizado(),
                contactoGuardado.getFechaCreacion()
        );
    }

    @Override
    public void eliminarContacto(Integer idContacto) {

        log.info("Eliminar contacto...");

        contactoRepository.findById(idContacto)
                .orElseThrow(() ->
                        new IllegalArgumentException("El contacto no existe.")
                );

        contactoRepository.deleteById(idContacto);

    }
}
