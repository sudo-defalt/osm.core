package org.defalt.core.service;

import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;
import org.defalt.core.entity.UserAccessMethod;
import org.defalt.core.event.publication.PostPublicationAwareEventProducer;
import org.defalt.core.model.entity.post.PostPublicationCreationDTO;
import org.defalt.core.model.entity.post.PostPublicationListingDTO;
import org.defalt.core.repository.PostPublicationRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PostPublicationService extends AbstractEntityService<PostPublication, PostPublicationRepository, PostPublicationCreationDTO> {

    public PostPublicationService(PostPublicationRepository repository,
                                  PostPublicationAwareEventProducer eventProducer) {
        super(repository, eventProducer);
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
        return persist(postPublication);
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
