package org.defalt.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Set;

@Entity(name = PostPublication.ENTITY_NAME)
@Table(name = PostPublication.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PostPublication extends Publication {
    public static final String ENTITY_NAME = "PostPublication";
    public static final String TABLE_NAME = "post_publications";

    @Column(nullable = false)
    private String caption;
    @ElementCollection
    private Set<String> files;
}
