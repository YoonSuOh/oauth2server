package com.example.oauthserver.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "id")
    private String id;

    @Column(name = "pw")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @UpdateTimestamp    // 현재시간 디폴트값
    @Column(name="createAt", updatable = false) // insert시 최초 시간만 넣고 시간 수정 안되게
    private LocalDateTime createdAt;

    @UpdateTimestamp    // 현재시간 디폴트값
    @Column(name="updateAt", updatable = false) // insert시 최초 시간만 넣고 시간 수정 안되게
    private LocalDateTime updateAt;
}
