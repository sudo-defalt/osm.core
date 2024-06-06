package org.defalt.core.service;

import org.defalt.core.entity.AbstractEntity;
import org.defalt.core.event.EntityAwareEventProducer;
import org.defalt.core.model.abstracts.CreationDTO;
import org.defalt.core.model.abstracts.LoaderDTO;
import org.defalt.core.model.abstracts.SaverDTO;
import org.defalt.core.model.exception.BadCreationDTOException;
import org.defalt.core.repository.AbstractEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This class is an abstract implementation of service layer for generic entities.
 * for each specific entity class must be inherited to use predefined methods of
 * basic CRUD.
 *
 * @param <E> generic entity
 * @param <R> generic entity's repository
 * @param <C> entity's standard creation DTO
 */

@Slf4j
public abstract class AbstractEntityService<E extends AbstractEntity, R extends AbstractEntityRepository<E>, C extends CreationDTO<E>> {
    protected final R repository;
    protected final EntityAwareEventProducer<E> eventProducer;

    public AbstractEntityService(R repository, EntityAwareEventProducer<E> eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    // CRUD methods //
    @Transactional
    public Collection<E> getAll() {
        return repository.findAll();
    }
    public E findById(long id) {
        return repository.getReferenceById(id);
    }
    public Optional<E> findByUid(String uid) {
        return repository.findByUid(uid);
    }
    public Collection<E> findAllByUid(Collection<String> uid) {
        return repository.findByUidIn(uid);
    }
    private E getSingleEntityByUid(String uid) {
        return findByUid(uid).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public E persist(E entity) {
        E persistedEntity = repository.save(entity);
        eventProducer.publishOnCreate(persistedEntity);
        return persistedEntity;
    }

    @Transactional
    public E create(C creationDTO) {
        validateCreationDTO(creationDTO);
        E entity = creationDTO.create(createNewTransientInstance());
        E persistedEntity = repository.save(entity);
        eventProducer.publishOnCreate(persistedEntity);
        return persistedEntity;
    }

    @Transactional
    public E update(E entity) {
        E persistedEntity = repository.save(entity);
        eventProducer.publishOnUpdate(persistedEntity);
        return persistedEntity;
    }
    @Transactional
    public Collection<E> updateAll(Collection<E> entities) {
        return repository.saveAll(entities);
    }

    @Transactional
    public void deleteById(long id) {
        E entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.deleteById(id);
        eventProducer.publishOnDelete(entity);
    }
    @Transactional
    public void deleteByUid(String uid) {
        E entity = repository.findByUid(uid).orElseThrow(EntityNotFoundException::new);
        repository.deleteByUid(uid);
        eventProducer.publishOnDelete(entity);
    }

    // DTO based CRUDs //

    @Transactional(readOnly = true)
    public <D extends LoaderDTO<E>> D getEntity(String uid, Supplier<D> instanceSupplier) {
        D DTO = instanceSupplier.get();
        DTO.loadFrom(getSingleEntityByUid(uid));
        return DTO;
    }

    public <D extends LoaderDTO<E>> Collection<D> getAllEntities(Collection<String> uidList, Supplier<D> instanceSupplier) {
        return findAllByUid(uidList).stream()
                .map(entity -> {
                    D DTO = instanceSupplier.get();
                    DTO.loadFrom(entity);
                    return DTO;
                }).collect(Collectors.toList());
    }

    @Transactional
    public <D extends SaverDTO<E>> D updateEntity(D updateDTO) {
        E entity = getSingleEntityByUid(updateDTO.getEntityIdentifier());
        updateDTO.saveTo(entity);
        update(entity);
        return updateDTO;
    }

    public <D extends SaverDTO<E>> Collection<D> updateAllEntities(Collection<D> updateDTOs) {
        return updateDTOs.stream()
                .filter(dto -> {
                    try {
                        updateEntity(dto);
                        return true;
                    } catch (Exception e) {
                        log.error("error in updating entity with uid: " + dto.getEntityIdentifier() , e);
                        return false;
                    }
                }).collect(Collectors.toList());
    }

    // util methods //

    /**
     * This method creates a default instance of entity, instance is not persisted.
     * after creation and modification, instance must be persisted by provided persistance
     * methods of implementing service class
     *
     * @return transient instance of <E>
     */
    public abstract E createNewTransientInstance();

    /**
     * validates creation DTO of entity
     * @throws BadCreationDTOException
     * @param creationDTO
     */
    protected abstract void validateCreationDTO(C creationDTO);
}
