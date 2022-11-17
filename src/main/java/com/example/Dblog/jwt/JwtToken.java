package com.example.Dblog.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long tokenId;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String keyUsername;

}