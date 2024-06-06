package org.defalt.core.event;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.defalt.core.entity.AbstractEntity;
import org.defalt.core.util.ObjectMapperUtils;

public abstract class EntityEvents<E extends AbstractEntity> {
    public abstract Long getKey(E entity);

    public abstract String getCreatedTopic();
    public abstract String getUpdatedTopic();
    public abstract String getDeletedTopic();

    public abstract Object getCreatedEvent(E entity);
    public abstract Object getUpdatedEvent(E entity);
    public abstract Object getDeletedEvent(E entity);

    public ProducerRecord<Long, String> onCreate(E entity) throws Exception {
        return new ProducerRecord<>(
                getCreatedTopic(),
                getKey(entity),
                ObjectMapperUtils.convert(getCreatedEvent(entity))
        );
    }
    public ProducerRecord<Long, String> onUpdate(E entity) throws Exception {
        return new ProducerRecord<>(
                getUpdatedTopic(),
                getKey(entity),
                ObjectMapperUtils.convert(getUpdatedEvent(entity))
        );
    }
    public ProducerRecord<Long, String> onDelete(E entity) throws Exception {
        return new ProducerRecord<>(
                getDeletedTopic(),
                getKey(entity),
                ObjectMapperUtils.convert(getDeletedEvent(entity))
        );
    }
}
