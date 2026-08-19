package com.example.chat.securityLayer.security;

import com.example.chat.persistenceLayer.repository.ParticipaRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import com.example.chat.persistenceLayer.repository.ParticipaRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;
    private final ParticipaRepository participaRepository;

    public JwtChannelInterceptor(JwtDecoder jwtDecoder, ParticipaRepository participaRepository){

        this.jwtDecoder = jwtDecoder;
        this.participaRepository = participaRepository;

    }

    public Message<?> preSend(Message<?> message, MessageChannel channel){

        String prefijo = "Bearer ";

        String authorization = "";

        StompHeaderAccessor stompHeaderAccessor = MessageHeaderAccessor.getAccessor(
                message,
                StompHeaderAccessor.class
        );

        StompCommand command = stompHeaderAccessor.getCommand();

        System.out.println("Comando STOMP: " + command);
        System.out.println("Destino: " + stompHeaderAccessor.getDestination());
        System.out.println("Usuario: " + stompHeaderAccessor.getUser());

        if (command.equals(StompCommand.SUBSCRIBE)){

            String destino = stompHeaderAccessor.getDestination();

            String prefix = "/topic/conversacion/";

            if (destino.startsWith(prefix)) {

                Integer idUsuario = Integer.parseInt(stompHeaderAccessor.getUser().getName());

                Integer idConversacion = Integer.parseInt(destino.substring(prefix.length()));

                boolean pertenece = participaRepository.existsById_IdUsuarioAndId_IdConversacion(idUsuario, idConversacion);

                if (!pertenece){

                    System.out.println(
                            "Suscripción rechazada: el usuario "
                                    + idUsuario
                                    + " no pertenece a la conversación "
                                    + idConversacion
                    );

                    return null;

                }



            }


        }

        if (command.equals(StompCommand.CONNECT)){

            authorization = stompHeaderAccessor .getFirstNativeHeader("Authorization");

        }

        if (authorization.startsWith(prefijo)){

            String token = authorization.substring(prefijo.length());

            Jwt jwt = jwtDecoder.decode(token);

            String subject = jwt.getSubject();

            Authentication authenticaded_user = new UsernamePasswordAuthenticationToken(
                    subject,
                    null,
                    new ArrayList<>()
            );

            stompHeaderAccessor.setUser(authenticaded_user);
        }

        System.out.println(
                "Usuario puesto en accessor: "
                        + stompHeaderAccessor.getUser()
        );

        return message;

    }

}
