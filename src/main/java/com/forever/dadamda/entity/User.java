package com.forever.dadamda.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String pictureURL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String pictureURL, Role role) {

        this.name = name;
        this.email = email;
        this.pictureURL = pictureURL;
        this.role = role;
    }

    public User update(String name, String pictureURL) {

        this.name = name;
        this.pictureURL = pictureURL;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}