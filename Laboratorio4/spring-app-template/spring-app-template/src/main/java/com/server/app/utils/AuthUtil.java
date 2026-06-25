package com.server.app.utils;

import com.server.app.exceptions.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    private AuthUtil() {
    }

    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UnauthorizedException("No hay usuario autenticado");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Integer userId) {
            return userId;
        }

        throw new UnauthorizedException("Token inválido");
    }
}