package com.example.chat.businessLayer.service.impl;

import com.example.chat.businessLayer.service.MensajeService;
import com.example.chat.persistenceLayer.entity.enums.EstadoMensaje;
import com.example.chat.persistenceLayer.repository.ConversacionRepository;
import com.example.chat.persistenceLayer.repository.MensajeRepository;
import com.example.chat.persistenceLayer.entity.Conversacion;
import com.example.chat.persistenceLayer.entity.Usuario;
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

    @Override
    public MensajeResponseDTO enviarMensaje(MensajeRequestDTO mensaje){

        log.info("Enviando mensaje...");

        Mensaje entidadMensaje = new Mensaje();

        Usuario remitente = usuarioRepository.findById(mensaje.getIdRemitente())
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

        Mensaje guardado = mensajeRepository.save(entidadMensaje);

        return new MensajeResponseDTO(
                guardado.getIdMensaje(),
                guardado.getFecha(),
                guardado.getHora(),
                guardado.getContenido(),
                guardado.getEstado(),
                guardado.getRemitente().getIdUsuario()
        );


    }

    @Override
    public MensajeResponseDTO editarMensaje(Integer idMensaje, MensajeEditarDTO nuevoContenido) {

        log.info("Editando mensaje...");

        Mensaje entidadMensaje = mensajeRepository.findById(idMensaje)
                .orElseThrow(() ->
                        new IllegalArgumentException("El mensaje no existe."));

        entidadMensaje.setContenido(nuevoContenido.getContenido());

        mensajeRepository.save(entidadMensaje);

        return new MensajeResponseDTO(
                entidadMensaje.getIdMensaje(),
                entidadMensaje.getFecha(),
                entidadMensaje.getHora(),
                entidadMensaje.getContenido(),
                entidadMensaje.getEstado(),
                entidadMensaje.getRemitente().getIdUsuario()
        );


    }

    @Override
    public List<MensajeResponseDTO> listarMensajes(Integer idConversacion) {

       conversacionRepository.findById(idConversacion)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La conversación no existe."));

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
                    mensaje.getRemitente().getIdUsuario()
            ));
        }

        return listaRetorno;
    }

    @Override
    public void eliminarMensaje(Integer idMensaje) {

        log.info("Eliminando mensaje...");

        mensajeRepository.findById(idMensaje)
                .orElseThrow(() ->
                        new IllegalArgumentException("El mensaje no existe."));

        mensajeRepository.deleteById(idMensaje);

    }



}
