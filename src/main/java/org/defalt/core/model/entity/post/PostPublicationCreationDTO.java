package org.defalt.core.model.entity.post;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.model.abstracts.CreationDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPublicationCreationDTO implements CreationDTO<PostPublication> {
    private boolean isPrivate;
    private String caption;

    @Override
    public PostPublication create(PostPublication emptyInstance) {
        emptyInstance.setCaption(caption);
        emptyInstance.setPrivate(isPrivate);
        return emptyInstance;
    }
}
