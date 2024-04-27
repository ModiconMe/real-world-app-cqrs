package com.modiconme.realworld.domain.common;

import com.modiconme.realworld.domain.registeruser.ValidatedRegisterUserRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Builder(toBuilder = true)

@EqualsAndHashCode(of = "email")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;
    private String bio;
    private String image;

    @CreationTimestamp
    @Column(nullable = false)
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    public static UserEntity register(ValidatedRegisterUserRequest request) {
        return new UserEntity(null, request.getEmail().getValue(), request.getPassword().getValue(),
                request.getUsername().getValue(), null, null, null, null);
    }
}


