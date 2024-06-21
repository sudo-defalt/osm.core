package org.defalt.core.controller;

import lombok.AllArgsConstructor;
import org.defalt.core.model.entity.post.PostPublicationFullDTO;
import org.defalt.core.model.entity.user.UserBasicIdentityDTO;
import org.defalt.core.service.ExploreService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("explore")
@AllArgsConstructor
public class ExploreController {
    private final ExploreService exploreService;

    @GetMapping
    public Page<PostPublicationFullDTO> getLatest(Pageable pageable) {
        return exploreService.getLatest(pageable);
    }

    @GetMapping("/{username}")
    public Page<UserBasicIdentityDTO> searchUser(@PathVariable String username, Pageable pageable) {
        return exploreService.searchUser(username, pageable);
    }
}
