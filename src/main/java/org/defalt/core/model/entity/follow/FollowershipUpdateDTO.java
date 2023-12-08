package org.defalt.core.model.entity.follow;

import org.defalt.core.entity.Followership;
import org.defalt.core.model.abstracts.SaverDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowershipUpdateDTO implements SaverDTO<Followership> {
    private String uid;

    @Override
    public String getEntityIdentifier() {
        return uid;
    }

    @Override
    public void saveTo(Followership entity) {

    }
}
