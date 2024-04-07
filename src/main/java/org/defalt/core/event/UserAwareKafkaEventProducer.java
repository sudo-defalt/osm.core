package org.defalt.core.event;

import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.entity.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserAwareKafkaEventProducer implements EntityAwareEventProducer<User> {
    public static UserAwareKafkaEventProducer instance() {
        return CurrentApplicationContext.getBean(UserAwareKafkaEventProducer.class);
    }

    private final KafkaTemplate<String, String> template;

    public UserAwareKafkaEventProducer(ProducerFactory<String, String> producerFactory) {
        this.template = new KafkaTemplate<>(producerFactory);
    }

    @Override
    public void publishOnCreate(User entity) {

    }

    @Override
    public void publishOnUpdate(User entity) {

    }

    @Override
    public void publishOnDelete(User entity) {

    }
}
