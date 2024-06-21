package org.defalt.core.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.defalt.core.entity.AbstractEntity;
import org.defalt.core.event.exception.EventCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Slf4j
public abstract class EntityAwareEventProducer<E extends AbstractEntity> {
    protected final KafkaTemplate<Long, String> template;
    protected final EntityEvents<E> events;

    protected EntityAwareEventProducer(ProducerFactory<Long, String> producerFactory, EntityEvents<E> events) {
        this.template = new KafkaTemplate<>(producerFactory);
        this.events = events;
    }

    public void publishOnCreate(E entity) {
        try {
            template.send(events.onCreate(entity));
        } catch (Exception e) {
            log.error("error in assembling onCreate event for entity {}({})", entity.getClass().getName(), entity.getUid());
            throw new EventCreationException(e);
        }
    }

    public void publishOnUpdate(E entity) {
        try {
            template.send(events.onUpdate(entity));
        } catch (Exception e) {
            log.error("error in assembling onUpdate event for entity {}({})", entity.getClass().getName(), entity.getUid());
            throw new EventCreationException(e);
        }
    }

    public void publishOnDelete(E entity) {
        try {
            template.send(events.onUpdate(entity));
        } catch (Exception e) {
            log.error("error in assembling onDelete event for entity {}({})", entity.getClass().getName(), entity.getUid());
            throw new EventCreationException(e);
        }
    }

    @Bean
    public NewTopic onCreateTopic() {
        return TopicBuilder
                .name(events.getCreatedTopic())
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic onUpdateTopic() {
        return TopicBuilder
                .name(events.getUpdatedTopic())
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic onDeleteTopic() {
        return TopicBuilder
                .name(events.getDeletedTopic())
                .partitions(1)
                .build();
    }
}
