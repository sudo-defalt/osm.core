package org.defalt.core.event.user;

import org.defalt.core.entity.User;
import org.defalt.core.event.EntityEvents;
import org.springframework.stereotype.Component;

@Component
public class UserEvents extends EntityEvents<User> {

    @Override
    public Long getKey(User entity) {
        return entity.getId();
    }

    @Override
    public String getCreatedTopic() {
        return "user_created";
    }

    @Override
    public String getUpdatedTopic() {
        return "user_updated";
    }

    @Override
    public String getDeletedTopic() {
        return "user_deleted";
    }

    @Override
    public Object getCreatedEvent(User entity) {
        return new Created();
    }

    @Override
    public Object getUpdatedEvent(User entity) {
        return new Updated();
    }

    @Override
    public Object getDeletedEvent(User entity) {
        return new Deleted();
    }

    private static record Created(){}
    private static record Updated(){}
    private static record Deleted(){}
}
