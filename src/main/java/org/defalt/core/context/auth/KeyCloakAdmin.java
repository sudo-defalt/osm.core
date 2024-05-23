package org.defalt.core.context.auth;

import jakarta.ws.rs.core.Response;
import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.context.auth.exception.UserNotRegisteredException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeyCloakAdmin {
    protected final Keycloak client;

    protected KeyCloakAdmin(@Value("${keycloak-server.url}") String url,
                            @Value("${keycloak-server.username}") String username,
                            @Value("${keycloak-server.password}") String password) {

        client = KeycloakBuilder.builder()
                .serverUrl(url)
                .realm("osm")
                .clientId("core")
                .username(username)
                .password(password)
                .build();
    }

    public static KeyCloakAdmin getInstance() {
        return CurrentApplicationContext.getBean(KeyCloakAdmin.class);
    }

    public UserRepresentation getUser(String username) {
        List<UserRepresentation> result = this.client.realm("osm").users().searchByUsername(username, true);
        if (result.size() == 1)
            return result.get(0);
        else if (result.size() == 0)
            throw new UserNotRegisteredException("no user found with given username.");
        else
            throw new RuntimeException("keycloak is in illegal state, more than one user exist for given username.");
    }

    public void registerUser(UserRepresentation user) {
        Response response = this.client.realm("osm").users().create(user);
        if (!HttpStatus.valueOf(response.getStatus()).is2xxSuccessful())
            throw new RuntimeException("user registry failed");
    }
}
