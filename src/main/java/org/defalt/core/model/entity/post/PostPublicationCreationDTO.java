package org.defalt.core.model.entity.post;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.model.abstracts.CreationDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostPublicationCreationDTO implements CreationDTO<PostPublication> {
    private String caption;
    private List<String> files;

    @Override
    public PostPublication create(PostPublication emptyInstance) {
        emptyInstance.setCaption(caption);
        emptyInstance.getFiles().addAll(files);
        return emptyInstance;
    }
}
