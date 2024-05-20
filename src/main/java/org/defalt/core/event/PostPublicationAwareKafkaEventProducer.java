package org.defalt.core.event;

import org.defalt.core.context.CurrentApplicationContext;
import org.defalt.core.entity.PostPublication;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PostPublicationAwareKafkaEventProducer implements EntityAwareEventProducer <PostPublication> {
    public static PostPublicationAwareKafkaEventProducer getInstance() {
        return CurrentApplicationContext.getBean(PostPublicationAwareKafkaEventProducer.class);
    }

    private final KafkaTemplate<String, String> template;

    public PostPublicationAwareKafkaEventProducer(ProducerFactory<String, String> producerFactory) {
        this.template = new KafkaTemplate<>(producerFactory);
    }

    @Override
    public void publishOnCreate(PostPublication entity) {

    }

    @Override
    public void publishOnUpdate(PostPublication entity) {

    }

    @Override
    public void publishOnDelete(PostPublication entity) {

    }
}
