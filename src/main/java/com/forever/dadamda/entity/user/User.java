package com.forever.dadamda.entity.user;

import com.forever.dadamda.entity.BaseTimeEntity;
import com.forever.dadamda.entity.scrap.Scrap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
@ToString(exclude = "scrapList")
@Table(name = "users")
public class User extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Scrap> scrapList = new ArrayList<>();

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 320, nullable = false)
    private String email;

    @Column(length = 2083)
    private String profileUrl;

    @Column(nullable = false)
    private Provider provider;

    //@Column(length = 36, nullable = false)
    private String uuid;

    @Column(length = 10, nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String profileUrl, Provider provider, Role role, String nickname) {
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
        this.provider = provider;
        this.role = role;
        this.nickname = nickname;
    }

    public User update(String name, String profileUrl) {
        this.name = name;
        this.profileUrl = profileUrl;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}