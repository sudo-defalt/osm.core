package org.defalt.core.service;

import lombok.AllArgsConstructor;
import org.defalt.core.model.entity.post.PostPublicationFullDTO;
import org.defalt.core.repository.PostPublicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ExploreService {
    private final PostPublicationRepository postRepository;

    @Transactional
    public Page<PostPublicationFullDTO> getLatest(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(publication -> {
                    PostPublicationFullDTO dto = new PostPublicationFullDTO();
                    dto.loadFrom(publication);
                    return dto;
                });
    }
}
