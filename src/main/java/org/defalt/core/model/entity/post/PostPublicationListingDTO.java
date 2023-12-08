package org.defalt.core.model.entity.post;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;
import org.defalt.core.model.entity.user.UserFullDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostPublicationListingDTO {
    private Collection<PostPublicationLightDTO> posts;

    public PostPublicationListingDTO(Collection<PostPublication> posts) {
        this.setPosts(posts.stream()
                .map(PostPublicationLightDTO::build)
                .collect(Collectors.toList())
        );
    }
}
