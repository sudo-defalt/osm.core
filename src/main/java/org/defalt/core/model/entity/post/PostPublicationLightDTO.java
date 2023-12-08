package org.defalt.core.model.entity.post;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.model.abstracts.LoaderDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPublicationLightDTO implements LoaderDTO<PostPublication> {
    private String uid;
    private String publisherUid;
    private String captions;
    private long likes;

    public static PostPublicationLightDTO build(PostPublication entity) {
        PostPublicationLightDTO DTO = new PostPublicationLightDTO();
        DTO.loadFrom(entity);
        return DTO;
    }

    @Override
    public void loadFrom(PostPublication entity) {
        this.setUid(entity.getUid());
        this.setPublisherUid(entity.getPublisher().getUid());
        this.setCaptions(entity.getCaption());
        this.setLikes(entity.getLikes());
    }
}
