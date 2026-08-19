package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.businessExceptions.OperacionNoAutorizadaException;
import com.example.chat.businessLayer.service.MensajeService;
import com.example.chat.persistenceLayer.entity.enums.EstadoMensaje;
import com.example.chat.persistenceLayer.entity.enums.TipoConversacion;
import com.example.chat.persistenceLayer.repository.ConversacionRepository;
import com.example.chat.persistenceLayer.repository.MensajeRepository;
import com.example.chat.persistenceLayer.entity.Conversacion;
import com.example.chat.persistenceLayer.entity.Usuario;
import com.example.chat.persistenceLayer.repository.ParticipaRepository;
import com.example.chat.persistenceLayer.repository.UsuarioRepository;
import com.example.chat.persistenceLayer.entity.Mensaje;
import com.example.chat.presentationLayer.dto.MensajeResponseDTO;
import com.example.chat.presentationLayer.dto.MensajeRequestDTO;

import java.util.ArrayList;
import java.util.List;
import com.example.chat.presentationLayer.dto.MensajeEditarDTO;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MensajeServiceImpl implements MensajeService {

    private final UsuarioRepository usuarioRepository;
    private final MensajeRepository mensajeRepository;
    private final ConversacionRepository conversacionRepository;
    private final ParticipaRepository participaRepository;

    @Override
    public MensajeResponseDTO enviarMensaje(MensajeRequestDTO mensaje, Integer idUsuario){

        log.info("Enviando mensaje...");

        Mensaje entidadMensaje = new Mensaje();

        Usuario remitente = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El remitente no existe."));

        Conversacion conversacion = conversacionRepository.findById(mensaje.getIdConversacion())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La conversación no existe."));

        entidadMensaje.setContenido(mensaje.getContenido());
        entidadMensaje.setRemitente(remitente);
        entidadMensaje.setConversacion(conversacion);
        entidadMensaje.setEstado(EstadoMensaje.ENVIADO);
        entidadMensaje.setFecha(LocalDate.now());
        entidadMensaje.setHora(LocalTime.now());

        if (!participaRepository.existsById_IdUsuarioAndId_IdConversacion(remitente.getIdUsuario(), mensaje.getIdConversacion())
        && conversacion.getTipo().equals(TipoConversacion.PRIVADA)){
            throw new OperacionNoAutorizadaException("El usuario no pertenece a la conversación");
        }

        Mensaje guardado = mensajeRepository.save(entidadMensaje);

        return new MensajeResponseDTO(
                guardado.getIdMensaje(),
                guardado.getFecha(),
                guardado.getHora(),
                guardado.getContenido(),
                guardado.getEstado(),
                guardado.getRemitente().getIdUsuario(),
                guardado.getConversacion().getIdConversacion()
        );


    }

    @Override
    public MensajeResponseDTO editarMensaje(Integer idMensaje, Integer idUsuario, MensajeEditarDTO nuevoContenido) {

        log.info("Editando mensaje...");

        Mensaje entidadMensaje = mensajeRepository.findById(idMensaje)
                .orElseThrow(() ->
                        new IllegalArgumentException("El mensaje no existe."));

        if (!entidadMensaje.getRemitente().getIdUsuario().equals(idUsuario)){
            throw new OperacionNoAutorizadaException("Usuario no autorizado para editar el mensaje.");
        }

        entidadMensaje.setContenido(nuevoContenido.getContenido());

        mensajeRepository.save(entidadMensaje);

        return new MensajeResponseDTO(
                entidadMensaje.getIdMensaje(),
                entidadMensaje.getFecha(),
                entidadMensaje.getHora(),
                entidadMensaje.getContenido(),
                entidadMensaje.getEstado(),
                entidadMensaje.getRemitente().getIdUsuario(),
                entidadMensaje.getConversacion().getIdConversacion()
        );


    }

    @Override
    public List<MensajeResponseDTO> listarMensajes(Integer idConversacion, Integer idUsuario) {

       Conversacion conversacion = conversacionRepository.findById(idConversacion)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La conversación no existe."));

       if (!participaRepository.existsById_IdUsuarioAndId_IdConversacion(idUsuario, idConversacion)
       && conversacion.getTipo().equals(TipoConversacion.PRIVADA)){

           throw new OperacionNoAutorizadaException("El usuario no pertenece a la conversación");

       }

        List<Mensaje> mensajes = mensajeRepository.findByConversacionIdConversacion(idConversacion);

        List<MensajeResponseDTO> listaRetorno = new ArrayList<>();

        for (int i = 0; i < mensajes.size(); i++){

            Mensaje mensaje = mensajes.get(i);

            listaRetorno.add(new MensajeResponseDTO(
                    mensaje.getIdMensaje(),
                    mensaje.getFecha(),
                    mensaje.getHora(),
                    mensaje.getContenido(),
                    mensaje.getEstado(),
                    mensaje.getRemitente().getIdUsuario(),
                    mensaje.getConversacion().getIdConversacion()
            ));
        }

        return listaRetorno;
    }

    @Override
    public void eliminarMensaje(Integer idMensaje, Integer idUsuario) {

        log.info("Eliminando mensaje...");

        Mensaje mensaje = mensajeRepository.findById(idMensaje)
                .orElseThrow(() ->
                        new IllegalArgumentException("El mensaje no existe."));

        if (!mensaje.getRemitente().getIdUsuario().equals(idUsuario)){
            throw new OperacionNoAutorizadaException("Usuario no autorizado para eliminar el mensaje.");
        }

        mensajeRepository.deleteById(idMensaje);

    }



}
