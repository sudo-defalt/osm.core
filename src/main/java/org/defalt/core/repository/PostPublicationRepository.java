package org.defalt.core.repository;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;

import java.util.List;

public interface PostPublicationRepository extends AbstractEntityRepository<PostPublication> {
    List<PostPublication> getAllByPublisher(User publisher);
    List<PostPublication> getAllByPublisherOrderByCreatedAtDesc(User publisher);
    void deleteByPublisher(User publisher);
}
