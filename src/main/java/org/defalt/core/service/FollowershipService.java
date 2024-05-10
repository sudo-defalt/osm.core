package org.defalt.core.service;

import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.entity.Followership;
import org.defalt.core.entity.User;
import org.defalt.core.model.entity.follow.FollowershipCreationDTO;
import org.defalt.core.repository.FollowershipRepository;
import org.defalt.core.repository.UserRepository;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;

@Component
public class FollowershipService extends AbstractEntityService<Followership, FollowershipRepository, FollowershipCreationDTO> {
    private final FollowershipRepository repository;
    private final UserRepository userRepository;

    public FollowershipService(FollowershipRepository repository, UserRepository userRepository) {
        super(repository);
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public static FollowershipService getInstance() {
        return CurrentApplicationContext.getBean(FollowershipService.class);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Followership create(FollowershipCreationDTO creationDTO) {
        validateCreationDTO(creationDTO);
        Followership entity = createNewTransientInstance();
        entity.setFollower(userRepository.findByUid(creationDTO.getFollowerUid()).get());
        entity.setFollowee(userRepository.findByUid(creationDTO.getFolloweeUid()).get());
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
                .orElseThrow(() -> new EntityNotFoundException("uid: " + creationDTO.getFolloweeUid()));
        User follower = userRepository.findByUid(creationDTO.getFollowerUid())
                .orElseThrow(() -> new EntityNotFoundException("uid: " + creationDTO.getFollowerUid()));

        if (repository.findByFollowerAndFollowee(follower, followee).isPresent())
            throw new IllegalStateException("followership exists");

        if (creationDTO.getFollowerUid().equals(creationDTO.getFolloweeUid()))
            throw new IllegalStateException("you cannot follow yourself!");
    }
}
