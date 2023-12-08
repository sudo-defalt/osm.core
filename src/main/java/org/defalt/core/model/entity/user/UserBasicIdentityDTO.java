package org.defalt.core.model.entity.user;

import org.defalt.core.entity.User;
import org.defalt.core.model.abstracts.LoaderDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBasicIdentityDTO implements LoaderDTO<User> {
    private String uid;
    private String firstname;
    private String lastName;
    private String username;

    public static UserBasicIdentityDTO build(User publisher) {
        UserBasicIdentityDTO DTO = new UserBasicIdentityDTO();
        DTO.loadFrom(publisher);
        return DTO;
    }

    @Override
    public void loadFrom(User entity) {
        this.setUid(entity.getUid());
        this.setFirstname(entity.getFirstname());
        this.setLastName(entity.getLastname());
        this.setUsername(entity.getUsername());
    }
}
