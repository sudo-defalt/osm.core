package org.defalt.core.model.entity.user;

import lombok.Getter;
import lombok.Setter;
import org.defalt.core.entity.User;
import org.defalt.core.model.abstracts.LoaderDTO;
import org.defalt.core.service.FollowershipService;
import org.defalt.core.util.CipherUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class UserProfileDTO implements LoaderDTO<User> {
    private String uid;
    private LocalDateTime createdAt;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String phoneNumber;
    private String profilePhoto;
    private long followers;
    private long followings;

    @Override
    public void loadFrom(User entity) {
        setUid(entity.getUid());
        setCreatedAt(entity.getCreatedAt());
        setFirstname(entity.getFirstname());
        setLastname(entity.getLastname());
        setUsername(entity.getUsername());
        setPhoneNumber(entity.getPhoneNumber());
        Optional.ofNullable(entity.getProfilePhoto()).map(CipherUtils.getInstance()::encryptAccessForMyself).ifPresent(this::setProfilePhoto);
        setFollowers(FollowershipService.getInstance().followersCount(entity));
        setFollowings(FollowershipService.getInstance().followingsCount(entity));
    }
}
