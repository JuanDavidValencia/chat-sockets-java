package com.example.chat.businessLayer.service;

import com.example.chat.presentationLayer.dto.ConversacionResponseDTO;
import com.example.chat.presentationLayer.dto.ParticipaRequestDTO;

import java.util.List;

public interface ParticipaService {

    void agregarParticipante(ParticipaRequestDTO participa, Integer idUsuarioAutenticado);

    List<ConversacionResponseDTO> listarConversacionesUsuario(Integer idUsuario, Integer idUsuarioAutenticado);

    void eliminarParticipante(Integer idUsuario, Integer idConversacion, Integer idUsuarioAutenticado);

}
