package org.defalt.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = Followership.ENTITY_NAME)
@Table(name = Followership.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Followership extends AbstractEntity {
    public static final String ENTITY_NAME = "Followership";
    public static final String TABLE_NAME = "followership";

    @ManyToOne
    @EqualsAndHashCode.Include
    private User follower;
    @ManyToOne
    @EqualsAndHashCode.Include
    private User followee;
    @Enumerated
    private FollowershipStatus status;
}
