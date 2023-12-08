package org.defalt.core.model.abstracts;

import org.defalt.core.entity.AbstractEntity;

public interface SaverDTO<E extends AbstractEntity> {
    String getEntityIdentifier();
    void saveTo(E entity);
}
