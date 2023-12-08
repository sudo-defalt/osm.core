package org.defalt.core.model.abstracts;

import org.defalt.core.entity.AbstractEntity;

public interface LoaderDTO<E extends AbstractEntity> {
    void loadFrom(E entity);
}
