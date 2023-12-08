package org.defalt.core.model.entity.post;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.model.abstracts.LoaderDTO;
import org.defalt.core.model.entity.user.UserBasicIdentityDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPublicationFullDTO implements LoaderDTO<PostPublication> {
    private String uid;
    private String captions;
    private long likes;
    private UserBasicIdentityDTO publisher;

    @Override
    public void loadFrom(PostPublication entity) {
        this.setUid(entity.getUid());
        this.setCaptions(entity.getCaption());
        this.setLikes(entity.getLikes());
        this.setPublisher(UserBasicIdentityDTO.build(entity.getPublisher()));
    }
}
