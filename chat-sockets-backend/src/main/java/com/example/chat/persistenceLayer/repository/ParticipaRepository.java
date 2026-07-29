package com.example.chat.persistenceLayer.repository;

import com.example.chat.persistenceLayer.entity.Participa;
import com.example.chat.persistenceLayer.entity.ParticipaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipaRepository extends JpaRepository<Participa, ParticipaId> {

    List<Participa> findByUsuarioIdUsuario(Integer idUsuario);

}
