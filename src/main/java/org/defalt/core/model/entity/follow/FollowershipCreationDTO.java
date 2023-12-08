package org.defalt.core.model.entity.follow;

import org.defalt.core.entity.Followership;
import org.defalt.core.model.abstracts.CreationDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowershipCreationDTO implements CreationDTO<Followership> {
    private String followeeUid;
    private String followerUid;

    @Override
    public Followership create(Followership emptyInstance) {
        return emptyInstance;
    }
}
