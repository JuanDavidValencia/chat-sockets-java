package com.example.chat.persistenceLayer.repository;


import com.example.chat.persistenceLayer.entity.Contacto;
import com.example.chat.persistenceLayer.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

    List<Contacto> findByUsuarioIdUsuario(Integer idUsuario);

    List<Contacto> contacto(Usuario contacto);
}
