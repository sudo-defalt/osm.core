package org.defalt.core.model.entity.post;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.Tag;
import org.defalt.core.model.abstracts.LoaderDTO;
import org.defalt.core.model.entity.user.UserBasicIdentityDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostPublicationFullDTO implements LoaderDTO<PostPublication> {
    private String uid;
    private String caption;
    private long likes;
    private UserBasicIdentityDTO publisher;
    private Set<String> tags;

    @Override
    public void loadFrom(PostPublication entity) {
        this.setUid(entity.getUid());
        this.setCaption(entity.getCaption());
        this.setLikes(entity.getLikes());
        this.setPublisher(UserBasicIdentityDTO.build(entity.getPublisher()));
        this.setTags(entity.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
    }
}
