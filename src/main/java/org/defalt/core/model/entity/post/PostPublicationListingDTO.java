package org.defalt.core.model.entity.post;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;
import org.defalt.core.model.entity.user.UserFullDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostPublicationListingDTO {
    private Collection<PostPublicationLightDTO> posts;
    private int size;
    private int number;
    private long totalSize;

    public PostPublicationListingDTO(Page<PostPublication> posts) {
        setNumber(posts.getNumber());
        setSize(posts.getSize());
        setTotalSize(posts.getTotalElements());
        this.setPosts(posts.stream()
                .map(PostPublicationLightDTO::build)
                .collect(Collectors.toList())
        );
    }
}
