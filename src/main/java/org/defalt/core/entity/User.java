package org.defalt.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = User.ENTITY_NAME)
@Table(name = User.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends AbstractEntity {
    public static final String ENTITY_NAME = "User";
    public static final String TABLE_NAME = "users";

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String firstname;
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String lastname;
    @Column(unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private String email;
    @Column(unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private String phoneNumber;
    @Enumerated
    @Column(nullable = false)
    private UserAccessMethod accessMethod;
}
