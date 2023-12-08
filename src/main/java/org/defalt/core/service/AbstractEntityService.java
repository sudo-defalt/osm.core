package org.defalt.core.service;

import org.defalt.core.entity.AbstractEntity;
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
    private final R repository;

    public AbstractEntityService(R repository) {
        this.repository = repository;
    }

    // CRUD methods //
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
        return findByUid(uid).orElseThrow(() -> new EntityNotFoundException("uid: " + uid));
    }

    @Transactional
    public E persist(E entity) {
        return repository.save(entity);
    }

    @Transactional
    public E create(C creationDTO) {
        validateCreationDTO(creationDTO);
        E entity = creationDTO.create(createNewTransientInstance());
        return repository.save(entity);
    }

    @Transactional
    public E update(E entity) {
        return repository.save(entity);
    }
    @Transactional
    public Collection<E> updateAll(Collection<E> entities) {
        return repository.saveAll(entities);
    }

    @Transactional
    public void deleteById(long id) {
        repository.deleteById(id);
    }
    @Transactional
    public void deleteByUid(String uid) {
        repository.deleteByUid(uid);
    }

    // DTO based CRUDs //

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
