package org.defalt.core.model.mapper;

import org.defalt.core.entity.User;
import org.defalt.core.model.entity.user.UserProfileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserModelMapper {
    UserProfileDTO toProfile(User user);
}
