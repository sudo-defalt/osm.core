package org.defalt.core.event;

import org.defalt.core.entity.AbstractEntity;

public interface EntityAwareEventProducer<E extends AbstractEntity> {
    void publishOnCreate(E entity);
    void publishOnUpdate(E entity);
    void publishOnDelete(E entity);
}
