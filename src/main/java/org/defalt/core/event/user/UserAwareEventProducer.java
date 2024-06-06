package org.defalt.core.event.user;

import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.entity.User;
import org.defalt.core.event.EntityAwareEventProducer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserAwareEventProducer extends EntityAwareEventProducer<User> {
    public UserAwareEventProducer(ProducerFactory<Long, String> producerFactory, UserEvents events) {
        super(producerFactory, events);
    }

    public static UserAwareEventProducer instance() {
        return CurrentApplicationContext.getBean(UserAwareEventProducer.class);
    }
}
