package org.defalt.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class Publication extends AbstractEntity {
    @ManyToOne
    private User publisher;
    @Column(nullable = false, name = "is_private")
    private boolean isPrivate;
    private long likes;
}
