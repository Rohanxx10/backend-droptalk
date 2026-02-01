package com.example.chat_app.interceptor;

import com.example.chat_app.security.StompPrincipal;
import com.example.chat_app.service.impl.JwtService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    public JwtHandshakeInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        URI uri = request.getURI();
        String query = uri.getQuery(); // token=xxx

        if (query == null || !query.startsWith("token=")) {
            return false;
        }

        String token = query.substring(6);

        if (!jwtService.validateJwtToken(token)) {
            return false;
        }

        String username = jwtService.extractUsername(token);

        // STORE PRINCIPAL
        attributes.put("principal", new StompPrincipal(username));

        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {
    }
}
