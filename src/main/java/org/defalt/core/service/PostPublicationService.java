package org.defalt.core.service;

import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;
import org.defalt.core.model.entity.post.PostPublicationCreationDTO;
import org.defalt.core.repository.PostPublicationRepository;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
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

    public List<PostPublication> getPostsOfUser(User publisher) {
        return repository.getAllByPublisherOrderByCreatedAtDesc(publisher);
    }

    @Override
    public PostPublication create(PostPublicationCreationDTO creationDTO) {
        validateCreationDTO(creationDTO);
        User user = UserSecurityContext.getCurrentUser().getUser();
        PostPublication postPublication = creationDTO.create(createNewTransientInstance());
        postPublication.setPublisher(user);
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
    }
}
