package org.defalt.core.repository;

import org.defalt.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends AbstractEntityRepository<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query(value = """ 
            select u.* from osm.users u
            where u.id != :myId
            and u.id not in (select followee_id from osm.followership where follower_id = :myId and status = 2)
            and u.username like concat(:username, '%')
            """, nativeQuery = true)
    Page<User> searchAvailableByUsername(long myId, String username, Pageable pageable);

    @Query(value = """ 
            select u.* from osm.users u
            where u.id != :myId
            and (exists(select followee_id from osm.followership where follower_id = :myId and status = 0) or u.access_method = 0)
            and u.username = :username
            """, nativeQuery = true)
    Optional<User> checkAccessForUsername(long myId, String username);
}
