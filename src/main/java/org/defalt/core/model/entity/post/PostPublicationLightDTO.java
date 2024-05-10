package org.defalt.core.model.entity.post;

import org.apache.http.client.utils.DateUtils;
import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;
import org.defalt.core.model.abstracts.LoaderDTO;
import lombok.Getter;
import lombok.Setter;
import org.defalt.core.util.CipherUtils;
import org.defalt.core.util.TimeUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostPublicationLightDTO implements LoaderDTO<PostPublication> {
    private String uid;
    private String publisherUid;
    private String captions;
    private long likes;
    private List<String> files;

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
        this.setFiles(loadFiles(entity));
    }

    private List<String> loadFiles(PostPublication entity) {
        User owner = entity.getPublisher();
        User user = UserSecurityContext.getCurrentUser().getUser();
        Date expiration = TimeUtils.plusMinutes(new Date(), 10);
        CipherUtils cipherUtils = CipherUtils.getInstance();
        return entity.getFiles()
                .stream()
                .map(hash -> cipherUtils.encryptAccess(owner, hash, user, expiration))
                .collect(Collectors.toList());
    }
}
