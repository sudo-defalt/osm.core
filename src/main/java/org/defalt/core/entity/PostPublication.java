package org.defalt.core.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
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
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> files = new HashSet<>();
    @ManyToMany
    private Set<Tag> tags = new HashSet<>();
}
