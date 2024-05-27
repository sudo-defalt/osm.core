package org.defalt.core.model.mapper;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.model.entity.post.PostPublicationFullDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostPublicationMapper {
    PostPublicationFullDTO toFullDTO(PostPublication entity);
}
