package org.defalt.core;

import org.defalt.core.model.entity.follow.FollowershipCreationDTO;
import org.defalt.core.model.entity.follow.FollowershipFullDTO;
import org.defalt.core.model.entity.post.PostPublicationCreationDTO;
import org.defalt.core.model.entity.post.PostPublicationFullDTO;
import org.defalt.core.model.entity.user.UserCreationDTO;
import org.defalt.core.model.entity.user.UserFullDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
class OSMTests {
	private final RestTemplate client = new RestTemplate();
	private final static Logger logger = LogManager.getLogger(OSMTests.class);

	@Test
	void contextLoads() {
		UserFullDTO user = createUser();
		logger.info("successfully created user with username {}", user.getUsername());
		Collection<PostPublicationFullDTO> usersPosts = createPost(user.getUid(), 5);
		logger.info("successfully created {} posts for user  {}", 5, user.getUsername());
		UserFullDTO user2 = createUser2();
		logger.info("successfully created user with username {}", user2.getUsername());
		Collection<PostPublicationFullDTO> user2sPosts = createPost(user2.getUid(), 10);
		logger.info("successfully created {} posts for user {}", 10, user2.getUsername());
		createFollowership(user.getUid(), user2.getUid());
		logger.info("{} is following {} now", user.getUsername(), user2.getUsername());
		FollowershipFullDTO followership = createFollowership(user2.getUid(), user.getUid());
		logger.info("{} is following {} now", user2.getUsername(), user.getUsername());

		Optional.ofNullable(usersPosts.iterator().next()).ifPresent(post -> {
			deletePost(post.getUid());
			logger.info("delete post of {} with caption {}", user.getUsername(), post.getCaptions());
		});
		Optional.ofNullable(user2sPosts.iterator().next()).ifPresent(post -> {
			deletePost(post.getUid());
			logger.info("delete post of {} with caption {}", user2.getUsername(), post.getCaptions());
		});

		unfollow(followership.getUid());
		logger.info("{} has unfollowed {}", user2.getUsername(), user.getUsername());

		deleteUser(user.getUid());
		logger.info("deleted user {}", user.getUsername());
	}

	private FollowershipFullDTO createFollowership(String followerUid, String followeeUid) {
		FollowershipCreationDTO creationDTO = new FollowershipCreationDTO();
		creationDTO.setFollowerUid(followerUid);
		creationDTO.setFolloweeUid(followeeUid);
		return client.postForObject("http://localhost:8090/api/followership/entity", creationDTO, FollowershipFullDTO.class);
	}

	private UserFullDTO createUser() {
		UserCreationDTO creationDTO = new UserCreationDTO();
		creationDTO.setFirstname("adnan");
		creationDTO.setLastName("aliyari");
		creationDTO.setUsername("admin");
		creationDTO.setPhoneNumber("+989360665933");
		creationDTO.setEmail("adnan.ali.yari@gmail.com");
		return client.postForObject("http://localhost:8090/api/user/entity", creationDTO, UserFullDTO.class);
	}
	private UserFullDTO createUser2() {
		UserCreationDTO creationDTO = new UserCreationDTO();
		creationDTO.setFirstname("user-a");
		creationDTO.setLastName("user-a");
		creationDTO.setUsername("user-a");
		creationDTO.setPhoneNumber("+989360665000");
		creationDTO.setEmail("user-a@gmail.com");
		return client.postForObject("http://localhost:8090/api/user/entity", creationDTO, UserFullDTO.class);
	}
	private Collection<PostPublicationFullDTO> createPost(String userUid, int count) {
		ArrayList<PostPublicationFullDTO> posts = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			PostPublicationCreationDTO creationDTO = new PostPublicationCreationDTO();
			creationDTO.setCaption("Caption number " + (i + 1));
			creationDTO.setPrivate(false);
			creationDTO.setPublisherUid(userUid);
			PostPublicationFullDTO post = client
					.postForObject("http://localhost:8090/api/post/entity", creationDTO, PostPublicationFullDTO.class);
			posts.add(post);
		}
		return posts;
	}

	private void deleteUser(String uid) {
		client.delete("http://localhost:8090/api/user/entity/" + uid);
	}
	private void deletePost(String uid) {
		client.delete("http://localhost:8090/api/post/entity/" + uid);
	}
	private void unfollow(String uid) { client.delete("http://localhost:8090/api/followership/entity/" + uid);}
}
