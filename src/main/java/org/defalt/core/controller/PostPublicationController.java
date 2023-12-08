package org.defalt.core.controller;

import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.controller.abstracts.AbstractEntityDTOController;
import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;
import org.defalt.core.model.entity.post.PostPublicationCreationDTO;
import org.defalt.core.model.entity.post.PostPublicationFullDTO;
import org.defalt.core.model.entity.post.PostPublicationListingDTO;
import org.defalt.core.model.entity.post.PostPublicationUpdateDTO;
import org.defalt.core.repository.AbstractEntityRepository;
import org.defalt.core.service.AbstractEntityService;
import org.defalt.core.service.PostPublicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
@RequestMapping("post")
@AllArgsConstructor
public class PostPublicationController extends AbstractEntityDTOController<PostPublication, PostPublicationFullDTO, PostPublicationUpdateDTO, PostPublicationCreationDTO> {
    private final PostPublicationService service;

    @GetMapping("list")
    public PostPublicationListingDTO getAllPostPublications() {
        User user = UserSecurityContext.getCurrentUser().getUser();
        return new PostPublicationListingDTO(service.getPostsOfUser(user));
    }

    @Override
    protected AbstractEntityService<PostPublication, ? extends AbstractEntityRepository<PostPublication>, PostPublicationCreationDTO> getService() {
        return service;
    }

    @Override
    protected Supplier<PostPublicationFullDTO> getLoaderDTOSupplier() {
        return PostPublicationFullDTO::new;
    }
}
