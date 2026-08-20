package com.example.chat.businessLayer.service;

import java.util.List;
import com.example.chat.presentationLayer.dto.MensajeRequestDTO;
import com.example.chat.presentationLayer.dto.MensajeResponseDTO;
import com.example.chat.presentationLayer.dto.MensajeEditarDTO;

public interface MensajeService {

    MensajeResponseDTO enviarMensaje(MensajeRequestDTO mensaje, Integer idUsuario);

    MensajeResponseDTO editarMensaje(Integer idMensaje, Integer idUsuario, MensajeEditarDTO nuevoContenido);

    List<MensajeResponseDTO> listarMensajes(Integer idConversacion, Integer idUsuario);

    MensajeResponseDTO eliminarMensaje(Integer idMensaje, Integer idUsuario);

}
