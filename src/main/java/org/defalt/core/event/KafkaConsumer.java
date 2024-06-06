package org.defalt.core.event;

import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerSeekAware;

import java.util.Map;

public abstract class KafkaConsumer implements ConsumerSeekAware {
    protected abstract long getOffset();

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
    }
}
