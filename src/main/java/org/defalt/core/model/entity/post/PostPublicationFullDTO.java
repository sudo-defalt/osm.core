package org.defalt.core.model.entity.post;

import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.Tag;
import org.defalt.core.entity.User;
import org.defalt.core.model.abstracts.LoaderDTO;
import org.defalt.core.model.entity.user.UserBasicIdentityDTO;
import lombok.Getter;
import lombok.Setter;
import org.defalt.core.util.CipherUtils;
import org.defalt.core.util.TimeUtils;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostPublicationFullDTO implements LoaderDTO<PostPublication> {
    private String uid;
    private String caption;
    private long likes;
    private UserBasicIdentityDTO publisher;
    private Set<String> files;
    private Set<String> tags;

    @Override
    public void loadFrom(PostPublication entity) {
        this.setUid(entity.getUid());
        this.setCaption(entity.getCaption());
        this.setLikes(entity.getLikes());
        this.setPublisher(UserBasicIdentityDTO.build(entity.getPublisher()));
        this.setFiles(getCipheredFiles(entity));
        this.setTags(entity.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
    }

    private Set<String> getCipheredFiles(PostPublication entity) {
        Date expiration = TimeUtils.plusMinutes(new Date(), 30);
        User publisher = entity.getPublisher();
        User me = UserSecurityContext.getCurrentUser().getUser();
        return entity.getFiles()
                .stream()
                .map(file -> CipherUtils.getInstance().encryptAccess(publisher, file, me, expiration))
                .collect(Collectors.toSet());
    }
}
