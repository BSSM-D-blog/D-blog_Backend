package com.example.Dblog.global.jwt;

import com.example.Dblog.global.jwt.entity.JwtToken;
import com.example.Dblog.global.jwt.dto.Token;
import com.example.Dblog.global.jwt.entity.repository.JwtTokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
    private String accessSecretKey = "dblogaccesssecretkey";
    private final String refreshSecretKey = "dblogrefreshsecretkey";
    private final Long accessTokenValidTime = 30 * 60 * 1000L;
    private Long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 14;

    private final UserDetailsService userDetailsService;
    private final JwtTokenRepository refreshTokenRepository;

    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
    }

    public Token createAccessToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(userPk).build();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String jwtToken) {
        try{
            Jws<Claims> claims  = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String validateRefreshToken(JwtToken refreshTokenObj){
        String refreshToken = refreshTokenObj.getRefreshToken();
        try {
            Optional<JwtToken> refresh = refreshTokenRepository.findByRefreshToken(refreshToken);
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);
            if (refresh.isPresent() && !claims.getBody().getExpiration().before(new Date())) {
                return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
            }
        }catch (Exception e) {
            return null;
        }
        return null;
    }

    public String recreationAccessToken(String username, Object roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();
    }

    public Claims parseJwtToken(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(accessSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return null;
        }
    }
}
