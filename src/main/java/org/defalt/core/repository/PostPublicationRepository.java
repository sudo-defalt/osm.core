package org.defalt.core.repository;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostPublicationRepository extends AbstractEntityRepository<PostPublication> {
    List<PostPublication> getAllByPublisher(User publisher);
    Page<PostPublication> getAllByPublisherOrderByCreatedAtDesc(User publisher, Pageable pageable);
    void deleteByPublisher(User publisher);
}
