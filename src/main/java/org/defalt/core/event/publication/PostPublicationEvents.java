package org.defalt.core.event.publication;

import org.defalt.core.entity.PostPublication;
import org.defalt.core.entity.Tag;
import org.defalt.core.event.EntityEvents;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class PostPublicationEvents extends EntityEvents<PostPublication> {
    @Override
    public Long getKey(PostPublication entity) {
        return entity.getPublisher().getId();
    }

    @Override
    public String getCreatedTopic() {
        return "post_publication_created";
    }

    @Override
    public String getUpdatedTopic() {
        return "post_publication_updated";
    }

    @Override
    public String getDeletedTopic() {
        return "post_publication_deleted";
    }

    @Override
    public Object getCreatedEvent(PostPublication entity) {
        return new Created(entity.getUid(),
                entity.getPublisher().getUsername(),
                entity.getPublisher().getUid(),
                entity.getFiles(),
                entity.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
    }

    @Override
    public Object getUpdatedEvent(PostPublication entity) {
        return new Updated(entity.getUid(),
                entity.getPublisher().getUsername(),
                entity.getPublisher().getUid(),
                entity.getFiles(),
                entity.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
    }

    @Override
    public Object getDeletedEvent(PostPublication entity) {
        return new Deleted(entity.getUid(),
                entity.getPublisher().getUsername(),
                entity.getPublisher().getUid(),
                entity.getFiles(),
                entity.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
    }

    private static record Created(String Uid, String user, String userUid, Collection<String> files, Collection<String> tags) {}
    private static record Updated(String Uid, String user, String userUid, Collection<String> files, Collection<String> tags) {}
    private static record Deleted(String Uid, String user, String userUid, Collection<String> files, Collection<String> tags) {}
}
