package com.example.chat.persistenceLayer.repository;

import com.example.chat.persistenceLayer.entity.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Integer>{

}
