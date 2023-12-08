package org.defalt.core.model.abstracts;

import org.defalt.core.entity.AbstractEntity;

public interface CreationDTO<E extends AbstractEntity> {
    E create(E emptyInstance);
}
