package org.defalt.core.service;

import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.context.auth.KeyCloakAdmin;
import org.defalt.core.entity.User;
import org.defalt.core.event.user.UserAwareEventProducer;
import org.defalt.core.model.entity.user.UserCreationDTO;
import org.defalt.core.repository.FollowershipRepository;
import org.defalt.core.repository.PostPublicationRepository;
import org.defalt.core.repository.UserRepository;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Optional;

@Component
public class UserService extends AbstractEntityService<User, UserRepository, UserCreationDTO> {
    private final PasswordEncoder passwordEncoder;
    private final PostPublicationRepository postRepository;
    private final FollowershipRepository followershipRepository;

    public static UserService getInstance() {
        return CurrentApplicationContext.getBean(UserService.class);
    }

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder,
                       PostPublicationRepository postRepository,
                       FollowershipRepository followershipRepository, UserAwareEventProducer eventProducer) {
        super(repository, eventProducer);
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;
        this.followershipRepository = followershipRepository;
    }

    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    @Transactional
    public User create(UserCreationDTO creationDTO) {
        validateCreationDTO(creationDTO);
        creationDTO.setPassword(passwordEncoder.encode(creationDTO.getPassword()));
        User entity = creationDTO.create(createNewTransientInstance());
        return persist(entity);
    }

    @Transactional
    @Override
    public void deleteByUid(String uid) {
        User user = repository.findByUid(uid).orElseThrow(() -> new EntityNotFoundException("uid: " + uid));
        postRepository.deleteByPublisher(user);
        followershipRepository.deleteByFolloweeOrFollower(user, user);
        super.deleteByUid(uid);
    }

    @Override
    public User createNewTransientInstance() {
        return new User();
    }

    @Override
    protected void validateCreationDTO(UserCreationDTO creationDTO) {
        if (repository.findByEmail(creationDTO.getEmail()).isPresent())
            throw new IllegalArgumentException("email is registered already");
        if (repository.findByPhoneNumber(creationDTO.getPhoneNumber()).isPresent())
            throw new IllegalArgumentException("phone number is registered already");
        if (!StringUtils.hasText(creationDTO.getUsername()))
            throw new IllegalArgumentException("username is empty");
        if (repository.findByUsername(creationDTO.getUsername()).isPresent())
            throw new IllegalArgumentException("username is taken already");
    }

    @Transactional
    public User signup(UserCreationDTO request) {
        validateCreationDTO(request);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getUsername());
        userRepresentation.setFirstName(request.getFirstname());
        userRepresentation.setLastName(request.getLastname());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setEnabled(true);

        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(request.getPassword());
        userRepresentation.setCredentials(Collections.singletonList(passwordCredentials));

        User user = create(request);
        KeyCloakAdmin.getInstance().registerUser(userRepresentation);

        return user;
    }
}
