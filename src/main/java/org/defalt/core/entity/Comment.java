package org.defalt.core.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = Comment.ENTITY_NAME)
@Table(name = Comment.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Comment extends Publication {
    public static final String ENTITY_NAME = "Comment";
    public static final String TABLE_NAME = "comments";


    private String message;
    @ManyToOne
    private PostPublication post;
    @ManyToOne
    private Comment reply;
}
