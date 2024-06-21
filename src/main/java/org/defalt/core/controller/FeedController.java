package org.defalt.core.controller;

import lombok.AllArgsConstructor;
import org.defalt.core.controller.abstracts.BasicControllerExceptionHandler;
import org.defalt.core.model.entity.post.PostPublicationFullDTO;
import org.defalt.core.service.FeedService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("feed")
@AllArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FeedController implements BasicControllerExceptionHandler {
    private final FeedService feedService;

    @GetMapping
    Page<PostPublicationFullDTO> getLatest(Pageable pageable) {
        return feedService.getLatest(pageable);
    }
}
