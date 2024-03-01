package org.defalt.core.model.entity.user;

import lombok.Getter;
import lombok.Setter;
import org.defalt.core.entity.User;
import org.defalt.core.model.abstracts.LoaderDTO;

import java.time.LocalDateTime;

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

    @Override
    public void loadFrom(User entity) {
        setUid(entity.getUid());
        setCreatedAt(entity.getCreatedAt());
        setFirstname(entity.getFirstname());
        setLastname(entity.getLastname());
        setUsername(entity.getUsername());
        setPhoneNumber(entity.getPhoneNumber());
    }
}
