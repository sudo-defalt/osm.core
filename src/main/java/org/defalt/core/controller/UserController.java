package org.defalt.core.controller;

import lombok.AllArgsConstructor;
import org.defalt.core.controller.abstracts.AbstractEntityDTOController;
import org.defalt.core.entity.User;
import org.defalt.core.model.entity.user.UserCreationDTO;
import org.defalt.core.model.entity.user.UserFullDTO;
import org.defalt.core.model.entity.user.UserUpdateDTO;
import org.defalt.core.repository.AbstractEntityRepository;
import org.defalt.core.service.AbstractEntityService;
import org.defalt.core.service.UserService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
@RequestMapping("user")
@AllArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserController extends AbstractEntityDTOController<User, UserFullDTO, UserUpdateDTO, UserCreationDTO> {
    private final UserService service;

    @PostMapping("sign-up")
    public UserFullDTO signup(@RequestBody UserCreationDTO request) {
        User newUser = service.signup(request);
        UserFullDTO dto = new UserFullDTO();
        dto.loadFrom(newUser);
        return dto;
    }

    @Override
    protected AbstractEntityService<User, ? extends AbstractEntityRepository<User>, UserCreationDTO> getService() {
        return service;
    }

    @Override
    protected Supplier<UserFullDTO> getLoaderDTOSupplier() {
        return UserFullDTO::new;
    }
}
