package org.defalt.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity(name = Tag.ENTITY_NAME)
@Table(name = Tag.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Tag extends AbstractEntity {
    public static final String ENTITY_NAME = "Tag";
    public static final String TABLE_NAME = "tags";
    @Column(nullable = false, unique = true)
    private String name;
}
