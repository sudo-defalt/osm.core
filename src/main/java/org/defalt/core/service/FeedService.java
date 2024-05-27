package org.defalt.core.service;

import lombok.AllArgsConstructor;
import org.defalt.core.context.auth.UserSecurityContext;
import org.defalt.core.model.entity.post.PostPublicationFullDTO;
import org.defalt.core.repository.PostPublicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FeedService {
    private final PostPublicationRepository postPublicationRepository;

    @Transactional
    public Page<PostPublicationFullDTO> getLatest(Pageable pageable) {
        Long myId = UserSecurityContext.getCurrentUser().getUser().getId();
        return postPublicationRepository.getLatestForFeed(myId, pageable)
                .map(publication -> {
                    PostPublicationFullDTO dto = new PostPublicationFullDTO();
                    dto.loadFrom(publication);
                    return dto;
                });
    }
}
