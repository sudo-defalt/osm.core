package org.defalt.core.controller;

import lombok.AllArgsConstructor;
import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.context.auth.exception.UserNotRegisteredException;
import org.defalt.core.controller.abstracts.BasicControllerExceptionHandler;
import org.defalt.core.entity.User;
import org.defalt.core.model.entity.user.UserProfileDTO;
import org.defalt.core.service.UserService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profile")
@AllArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProfileController implements BasicControllerExceptionHandler {
    private final UserService service;

    @GetMapping
    public UserProfileDTO getProfile() {
        UserSecurityContext context = UserSecurityContext.getCurrentUser();
        User user = service.getByUsername(context.getUser().getUsername())
                .orElseThrow(UserNotRegisteredException::new);
        UserProfileDTO dto = new UserProfileDTO();
        dto.loadFrom(user);
        return dto;
    }
}
