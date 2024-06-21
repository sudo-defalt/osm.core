package org.defalt.core.controller;

import org.defalt.core.controller.abstracts.AbstractEntityDTOController;
import org.defalt.core.entity.Followership;
import org.defalt.core.model.entity.follow.FollowershipCreationDTO;
import org.defalt.core.model.entity.follow.FollowershipFullDTO;
import org.defalt.core.model.entity.follow.FollowershipUpdateDTO;
import org.defalt.core.repository.AbstractEntityRepository;
import org.defalt.core.service.AbstractEntityService;
import org.defalt.core.service.FollowershipService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
@RequestMapping("followership")
@AllArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FollowershipController extends AbstractEntityDTOController<Followership, FollowershipFullDTO, FollowershipUpdateDTO, FollowershipCreationDTO> {
    private final FollowershipService service;

    @Override
    protected AbstractEntityService<Followership, ? extends AbstractEntityRepository<Followership>, FollowershipCreationDTO> getService() {
        return service;
    }

    @Override
    protected Supplier<FollowershipFullDTO> getLoaderDTOSupplier() {
        return FollowershipFullDTO::new;
    }
}
