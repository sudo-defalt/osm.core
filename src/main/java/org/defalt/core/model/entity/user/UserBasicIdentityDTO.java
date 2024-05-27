package org.defalt.core.model.entity.user;

import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.entity.User;
import org.defalt.core.model.abstracts.LoaderDTO;
import lombok.Getter;
import lombok.Setter;
import org.defalt.core.util.CipherUtils;
import org.defalt.core.util.TimeUtils;

import java.util.Date;

@Getter
@Setter
public class UserBasicIdentityDTO implements LoaderDTO<User> {
    private String uid;
    private String username;
    private String profilePhoto;

    public static UserBasicIdentityDTO build(User publisher) {
        UserBasicIdentityDTO DTO = new UserBasicIdentityDTO();
        DTO.loadFrom(publisher);
        return DTO;
    }

    @Override
    public void loadFrom(User entity) {
        this.setUid(entity.getUid());
        this.setUsername(entity.getUsername());
        this.setProfilePhoto(loadProfilePhoto(entity));
    }

    private String loadProfilePhoto(User entity) {
        Date expiration = TimeUtils.plusHours(new Date(), 1);
        String file = entity.getProfilePhoto();
        User me = UserSecurityContext.getCurrentUser().getUser();
        return CipherUtils.getInstance().encryptAccess(entity, file, me, expiration);
    }
}
