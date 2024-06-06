package org.defalt.core.repository;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostPublicationRepository extends AbstractEntityRepository<PostPublication> {
    List<PostPublication> getAllByPublisher(User publisher);

    Page<PostPublication> getAllByPublisherOrderByCreatedAtDesc(User publisher, Pageable pageable);

    void deleteByPublisher(User publisher);

    @Query(value = """
                select pp.* from osm.post_publications pp
                inner join osm.users u on pp.publisher_id = u.id
                inner join osm.followership f on u.id = f.followee_id and f.follower_id = :myId and f.status = 0
                order by pp.last_update_at desc
            """, nativeQuery = true)
    Page<PostPublication> getLatestForFeed(long myId, Pageable pageable);

    @Query(value = """
                select pp.* from osm.post_publications pp
                inner join osm.users u on pp.publisher_id = u.id and u.access_method = 0 and u.id != :myId
                order by pp.last_update_at desc
            """, nativeQuery = true)
    Page<PostPublication> getLatestForExplore(long myId, Pageable pageable);
}
