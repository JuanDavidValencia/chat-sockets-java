package com.example.chat.businessLayer.service;

import com.example.chat.presentationLayer.dto.ConversacionRequestDTO;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;

public interface ConversacionService {

    ConversacionResponseDTO crearConversacion(ConversacionRequestDTO conversacion, Integer idUsuario);

    ConversacionResponseDTO buscarConversacion(Integer id, Integer idUsuario);

    void eliminarConversacion(Integer idConversacion, Integer idUsuario);


}
