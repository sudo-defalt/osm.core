package org.defalt.core.service;

import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.entity.Followership;
import org.defalt.core.entity.User;
import org.defalt.core.event.followership.FollowershipAwareEventProducer;
import org.defalt.core.model.entity.follow.FollowershipCreationDTO;
import org.defalt.core.repository.FollowershipRepository;
import org.defalt.core.repository.UserRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FollowershipService extends AbstractEntityService<Followership, FollowershipRepository, FollowershipCreationDTO> {
    private final UserRepository userRepository;

    public FollowershipService(FollowershipRepository repository,
                               UserRepository userRepository,
                               FollowershipAwareEventProducer eventProducer) {
        super(repository, eventProducer);
        this.userRepository = userRepository;
    }

    public static FollowershipService getInstance() {
        return CurrentApplicationContext.getBean(FollowershipService.class);
    }

    @Override
    public Followership create(FollowershipCreationDTO creationDTO) {
        validateCreationDTO(creationDTO);
        Followership entity = createNewTransientInstance();
        entity.setFollower(userRepository.findByUid(creationDTO.getFollowerUid())
                .orElseThrow(EntityNotFoundException::new));
        entity.setFollowee(userRepository.findByUid(creationDTO.getFolloweeUid())
                .orElseThrow(EntityNotFoundException::new));
        return repository.save(entity);
    }

    public long followersCount(User user) {
        return repository.countByFollowee(user);
    }

    public long followingsCount(User user) {
        return repository.countByFollower(user);
    }

    @Override
    public Followership createNewTransientInstance() {
        return new Followership();
    }

    @Override
    protected void validateCreationDTO(FollowershipCreationDTO creationDTO) {
        User followee = userRepository.findByUid(creationDTO.getFolloweeUid())
                .orElseThrow(EntityNotFoundException::new);
        User follower = userRepository.findByUid(creationDTO.getFollowerUid())
                .orElseThrow(EntityNotFoundException::new);

        if (repository.findByFollowerAndFollowee(follower, followee).isPresent())
            throw new IllegalStateException("followership exists");

        if (creationDTO.getFollowerUid().equals(creationDTO.getFolloweeUid()))
            throw new IllegalStateException("you cannot follow yourself!");
    }
}
