package org.defalt.core.model.entity.user;

import org.defalt.core.entity.User;
import org.defalt.core.model.abstracts.SaverDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO implements SaverDTO<User> {
    private String uid;
    private String firstname;
    private String lastname;

    @Override
    public String getEntityIdentifier() {
        return null;
    }

    @Override
    public void saveTo(User entity) {
        entity.setFirstname(getFirstname());
        entity.setLastname(getLastname());
    }
}
