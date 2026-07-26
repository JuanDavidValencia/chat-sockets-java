package com.example.chat.businessLayer.service;

import com.example.chat.persistenceLayer.entity.Conversacion;
import com.example.chat.presentationLayer.dto.ConversacionRequestDTO;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import java.util.List;

public interface ConversacionService {

    ConversacionResponseDTO crearConversacion(ConversacionRequestDTO conversacion);

    ConversacionResponseDTO buscarConversacion(Integer id);

    List<Conversacion> listarConversaciones(Integer idUsuario);

    void eliminarConversacion(Integer idConversacion);


}
