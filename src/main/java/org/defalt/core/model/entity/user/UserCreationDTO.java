package org.defalt.core.model.entity.user;

import org.defalt.core.entity.User;
import org.defalt.core.entity.UserAccessMethod;
import org.defalt.core.model.abstracts.CreationDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserCreationDTO implements CreationDTO<User> {
    private String firstname;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String password;
    private String email;

    @Override
    public User create(User emptyInstance) {
        emptyInstance.setFirstname(firstname);
        emptyInstance.setLastname(lastName);
        emptyInstance.setUsername(username);
        emptyInstance.setPhoneNumber(phoneNumber);
        emptyInstance.setPassword(password);
        emptyInstance.setEmail(email);
        emptyInstance.setAccessMethod(UserAccessMethod.Private);
        return emptyInstance;
    }
}
