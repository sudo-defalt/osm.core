package org.defalt.core.event.followership;

import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.entity.Followership;
import org.defalt.core.event.EntityAwareEventProducer;
import org.defalt.core.event.EntityEvents;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FollowershipAwareEventProducer extends EntityAwareEventProducer<Followership> {
    public FollowershipAwareEventProducer(ProducerFactory<Long, String> producerFactory,
                                          EntityEvents<Followership> events) {
        super(producerFactory, events);
    }

    public static FollowershipAwareEventProducer getInstance() {
        return CurrentApplicationContext.getBean(FollowershipAwareEventProducer.class);
    }
}
