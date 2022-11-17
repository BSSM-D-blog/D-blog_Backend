package com.example.Dblog.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String accessSecretkey = "dblogaccesssecretkey";
    private String refreshSecretKey = "dblogrefreshsecretkey";
    private Long accessTokenValidTime = 30 * 60 * 1000L;
    private Long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 14;

    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostConstruct
    protected void init() {
        accessSecretkey = Base64.getEncoder().encodeToString(accessSecretkey.getBytes());
    }

    public Token createAccessToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, accessSecretkey)
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
        return Jwts.parser().setSigningKey(accessSecretkey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String jwtToken) {
        try{
            Jws<Claims> claims  = Jwts.parser().setSigningKey(accessSecretkey).parseClaimsJws(jwtToken);
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
                .signWith(SignatureAlgorithm.HS256, accessSecretkey)
                .compact();
    }

    public Claims parseJwtToken(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(accessSecretkey)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return null;
        }
    }
}
