package org.defalt.core.model.mapper;

import org.defalt.core.entity.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserModelMapper {
    public abstract UserRepresentation userToUserRepresentation(User user);
}
