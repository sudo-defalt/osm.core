package org.defalt.core.model.entity.user;

import org.defalt.core.entity.User;
import org.defalt.core.model.abstracts.LoaderDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserFullDTO implements LoaderDTO<User> {
    private String uid;
    private String firstname;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String email;
    private LocalDateTime signedUpAt;

    @Override
    public void loadFrom(User entity) {
        this.setUid(entity.getUid());
        this.setFirstname(entity.getFirstname());
        this.setLastName(entity.getFirstname());
        this.setUsername(entity.getUsername());
        this.setPhoneNumber(entity.getPhoneNumber());
        this.setEmail(entity.getEmail());
        this.setSignedUpAt(entity.getCreatedAt());
    }
}
