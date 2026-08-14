package com.example.chat.businessLayer.service;

import com.example.chat.presentationLayer.dto.ContactoEditarDTO;
import com.example.chat.presentationLayer.dto.ContactoRequestDTO;
import com.example.chat.presentationLayer.dto.ContactoResponseDTO;

public interface ContactoService {

    ContactoResponseDTO crearContacto(ContactoRequestDTO contacto);

    ContactoResponseDTO buscarContacto(Integer idContacto);

    ContactoResponseDTO editarContacto(ContactoEditarDTO contacto);

    void eliminarContacto(Integer idContacto);

}
