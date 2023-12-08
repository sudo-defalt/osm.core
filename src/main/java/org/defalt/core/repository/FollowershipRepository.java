package org.defalt.core.repository;

import org.defalt.core.entity.Followership;
import org.defalt.core.entity.User;

import java.util.List;
import java.util.Optional;

public interface FollowershipRepository extends AbstractEntityRepository<Followership> {
    List<Followership> findByFollower(User follower);
    List<Followership> findByFollowee(User followee);
    Optional<Followership> findByFollowerAndFollowee(User follower, User followee);
    long countByFollowee(User followee);
    long countByFollower(User follower);
    void deleteByFollowerAndFollowee(User follower, User followee);
    void deleteByFolloweeOrFollower(User followee, User follower);
}
