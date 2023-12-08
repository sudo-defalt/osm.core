package org.defalt.core.model.entity.follow;

import org.defalt.core.entity.Followership;
import org.defalt.core.model.abstracts.LoaderDTO;
import org.defalt.core.model.entity.user.UserBasicIdentityDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowershipFullDTO implements LoaderDTO<Followership> {
    private String uid;
    private UserBasicIdentityDTO followee;
    private UserBasicIdentityDTO follower;

    @Override
    public void loadFrom(Followership entity) {
        setUid(entity.getUid());
        setFollowee(UserBasicIdentityDTO.build(entity.getFollowee()));
        setFollower(UserBasicIdentityDTO.build(entity.getFollower()));
    }
}
