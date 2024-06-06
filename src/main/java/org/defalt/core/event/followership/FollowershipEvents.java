package org.defalt.core.event.followership;

import org.defalt.core.entity.Followership;
import org.defalt.core.event.EntityEvents;
import org.springframework.stereotype.Component;

@Component
public class FollowershipEvents extends EntityEvents<Followership> {
    @Override
    public Long getKey(Followership entity) {
        return entity.getFollower().getId();
    }

    @Override
    public String getCreatedTopic() {
        return "followership_created";
    }

    @Override
    public String getUpdatedTopic() {
        return "followership_updated";
    }

    @Override
    public String getDeletedTopic() {
        return "followership_deleted";
    }

    @Override
    public Object getCreatedEvent(Followership entity) {
        return new Created();
    }

    @Override
    public Object getUpdatedEvent(Followership entity) {
        return new Updated();
    }

    @Override
    public Object getDeletedEvent(Followership entity) {
        return new Deleted();
    }

    private static record Created(){}
    private static record Updated(){}
    private static record Deleted(){}
}
