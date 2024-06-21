package org.defalt.core.service;

import lombok.AllArgsConstructor;
import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.model.entity.post.PostPublicationFullDTO;
import org.defalt.core.model.entity.user.UserBasicIdentityDTO;
import org.defalt.core.repository.PostPublicationRepository;
import org.defalt.core.repository.UserRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ExploreService {
    private final PostPublicationRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Page<PostPublicationFullDTO> getLatest(Pageable pageable) {
        Long myId = UserSecurityContext.getCurrentUser().getUser().getId();
        return postRepository.getLatestForExplore(myId, pageable)
                .map(publication -> {
                    PostPublicationFullDTO dto = new PostPublicationFullDTO();
                    dto.loadFrom(publication);
                    return dto;
                });
    }

    @Transactional
    public Page<UserBasicIdentityDTO> searchUser(String username, Pageable pageable) {
        Long myId = UserSecurityContext.getCurrentUser().getUser().getId();
        return userRepository.searchAvailableByUsername(myId, username, pageable)
                .map(user -> {
                    UserBasicIdentityDTO dto = new UserBasicIdentityDTO();
                    dto.loadFrom(user);
                    return dto;
                });
    }
}
