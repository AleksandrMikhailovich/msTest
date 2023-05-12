package com.itm.space.backendresources.mock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdentityMock {
    private final Keycloak keycloakClient;

    @Value("${keycloak.realm}")
    private String realm;

    public String createUser(UserRepresentation userRepresentation) {

        Response response = keycloakClient.realm(realm).users().create(userRepresentation);
        return CreatedResponseUtil.getCreatedId(response);

    }

    public List<GroupRepresentation> getUserGroups(UUID id) {
        List<GroupRepresentation> userGroups;
        userGroups = keycloakClient.realm(realm).users().get(String.valueOf(id)).groups();
        return userGroups;
    }
    public List<RoleRepresentation> getUserRoles(UUID id) {
        List<RoleRepresentation> userRoles;
        userRoles = keycloakClient.realm(realm).users().get(String.valueOf(id)).roles().getAll().getRealmMappings();
        return userRoles;
    }

    public UserRepresentation getUserById(UUID id) {
        UserRepresentation userRepresentation;
        userRepresentation = keycloakClient.realm(realm).users().get(String.valueOf(id)).toRepresentation();
        return userRepresentation;
    }
}
