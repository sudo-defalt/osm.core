package org.defalt.core.model.entity.post;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.model.abstracts.SaverDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPublicationUpdateDTO implements SaverDTO<PostPublication> {
    private String uid;
    private String caption;

    @Override
    public String getEntityIdentifier() {
        return uid;
    }

    @Override
    public void saveTo(PostPublication entity) {
        entity.setCaption(getCaption());
    }
}
