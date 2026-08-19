package com.example.chat.securityLayer.security;

import com.example.chat.persistenceLayer.repository.ParticipaRepository;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Collections;

@Component
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;
    private final ParticipaRepository participaRepository;

    public JwtChannelInterceptor(JwtDecoder jwtDecoder, ParticipaRepository participaRepository) {

        this.jwtDecoder = jwtDecoder;
        this.participaRepository = participaRepository;

    }

    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {

        StompHeaderAccessor stompHeaderAccessor = MessageHeaderAccessor.getAccessor(
                message,
                StompHeaderAccessor.class
        );


        if (stompHeaderAccessor == null) {

            throw new IllegalArgumentException("El mensaje no fue enviado correctamente.");

        }

        StompCommand command = stompHeaderAccessor.getCommand();

        if (StompCommand.SUBSCRIBE.equals(command)) {


            if (!validarSuscripcion(stompHeaderAccessor)) {

                return null;

            }


        }


        if (StompCommand.CONNECT.equals(command)) {

            String authorization = stompHeaderAccessor.getFirstNativeHeader("Authorization");

            String prefijoBearer = "Bearer ";

            if (authorization == null || !authorization.startsWith(prefijoBearer)){

                return null;

            }

            String token = authorization.substring(prefijoBearer.length());

            if (!autenticarUsuario(stompHeaderAccessor, token)){

                return null;

            }

        }

        return message;

    }

    private boolean validarSuscripcion(StompHeaderAccessor stompHeaderAccessor){

        Principal usuario = stompHeaderAccessor.getUser();

        if(usuario == null){

            return false;

        }

        String destino = stompHeaderAccessor.getDestination();

        String prefijoConversacion = "/topic/conversacion/";

        if (destino != null && destino.startsWith(prefijoConversacion)) {

            Integer idUsuario = Integer.parseInt(usuario.getName());

            Integer idConversacion = Integer.parseInt(destino.substring(prefijoConversacion.length()));

            boolean pertenece = participaRepository.existsById_IdUsuarioAndId_IdConversacion(idUsuario, idConversacion);

            if (!pertenece) {

                System.out.println(
                        "Suscripción rechazada: el usuario "
                                + idUsuario
                                + " no pertenece a la conversación "
                                + idConversacion
                );

                return false;

            }
        }

        return true;

    }

    private boolean autenticarUsuario(StompHeaderAccessor stompHeaderAccessor, String token){

        try{

            Jwt jwt = jwtDecoder.decode(token);

            String subject = jwt.getSubject();

            Authentication authenticadedUser = new UsernamePasswordAuthenticationToken(
                    subject,
                    null,
                    Collections.emptyList()
            );

            stompHeaderAccessor.setUser(authenticadedUser);

        } catch (JwtException e){

            log.warn("Error al momento de descifrar el JWT: {}", e.getMessage());

            return false;

        }

        return true;

    }

}
