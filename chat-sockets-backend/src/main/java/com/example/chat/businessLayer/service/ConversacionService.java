package com.example.chat.businessLayer.service;

import com.example.chat.presentationLayer.dto.ConversacionRequestDTO;
import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;

public interface ConversacionService {

    ConversacionResponseDTO crearConversacion(ConversacionRequestDTO conversacion);

    ConversacionResponseDTO buscarConversacion(Integer id);

    void eliminarConversacion(Integer idConversacion);


}
