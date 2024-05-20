package org.defalt.core.service;

import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;
import org.defalt.core.entity.UserAccessMethod;
import org.defalt.core.model.entity.post.PostPublicationCreationDTO;
import org.defalt.core.model.entity.post.PostPublicationListingDTO;
import org.defalt.core.repository.PostPublicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PostPublicationService extends AbstractEntityService<PostPublication, PostPublicationRepository, PostPublicationCreationDTO> {
    private final PostPublicationRepository repository;
    private final UserService userService;

    public PostPublicationService(PostPublicationRepository repository, UserService userService) {
        super(repository);
        this.repository = repository;
        this.userService = userService;
    }

    @Transactional
    public PostPublicationListingDTO getPostsOfUser(User publisher, Pageable pageable) {

        return new PostPublicationListingDTO(repository.getAllByPublisherOrderByCreatedAtDesc(publisher, pageable));
    }

    @Override
    public PostPublication create(PostPublicationCreationDTO creationDTO) {
        validateCreationDTO(creationDTO);
        User user = UserSecurityContext.getCurrentUser().getUser();
        PostPublication postPublication = creationDTO.create(createNewTransientInstance());
        postPublication.setPublisher(user);
        postPublication.setPrivate(user.getAccessMethod().equals(UserAccessMethod.Private));
        return repository.save(postPublication);
    }

    @Override
    public PostPublication createNewTransientInstance() {
        return new PostPublication();
    }

    @Override
    protected void validateCreationDTO(PostPublicationCreationDTO creationDTO) {
        if (Optional.ofNullable(creationDTO.getCaption()).isEmpty())
            throw new IllegalArgumentException("caption cannot be null");
        if (creationDTO.getFiles() == null)
            throw new IllegalArgumentException("files cannot be null");
        if (creationDTO.getFiles().isEmpty())
            throw new IllegalArgumentException("files cannot be empty");
    }
}
