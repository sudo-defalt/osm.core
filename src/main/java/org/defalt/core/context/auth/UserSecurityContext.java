package org.defalt.core.context.auth;

import org.defalt.core.context.auth.exception.UserNotRegisteredException;
import org.defalt.core.entity.User;
import org.defalt.core.service.UserService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.jwt.Jwt;

public class UserSecurityContext {
    private final SecurityContextImpl context;
    private final String username;
    private final User user;
    private UserRepresentation keycloakUser;

    protected UserSecurityContext(SecurityContextImpl context) {
        this.context = context;
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof Jwt) {
            this.username = ((Jwt) principal).getClaim("preferred_username");
            this.user = UserService.getInstance().getByUsername(username)
                    .orElseThrow(UserNotRegisteredException.supply("no user found"));
        }
        else {
            this.username = null;
            this.user = null;
        }

    }

    public static UserSecurityContext getCurrentUser() {
        return new UserSecurityContext((SecurityContextImpl) SecurityContextHolder.getContext());
    }

    public User getUser() {
        return user;
    }

    public UserRepresentation getKeycloakUser() {
        if (this.keycloakUser == null && this.username != null)
            return this.keycloakUser = KeyCloakAdmin.getInstance().getUser(this.username);
        else
            return this.keycloakUser;
    }

    public SecurityContextImpl getContext() {
        return context;
    }
}
