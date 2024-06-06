package org.defalt.core.event.publication;

import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.entity.PostPublication;
import org.defalt.core.event.EntityAwareEventProducer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PostPublicationAwareEventProducer extends EntityAwareEventProducer<PostPublication> {

    public PostPublicationAwareEventProducer(ProducerFactory<Long, String> producerFactory,
                                             PostPublicationEvents postPublicationEvents) {
        super(producerFactory, postPublicationEvents);
    }

    public static PostPublicationAwareEventProducer getInstance() {
        return CurrentApplicationContext.getBean(PostPublicationAwareEventProducer.class);
    }
}
