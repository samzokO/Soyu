package com.ssafy.soyu.util.jwt;

import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generate(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public TokenResponse createToken(Authentication authentication, String memberId) {
        // 사용자의 권한 정보 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        if(authorities.isEmpty())
            authorities = null;


        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);


        // Access Token 생성
        String accessToken = Jwts.builder()
                .claim("auth", authorities)
                .claim("memberId", memberId)
                .setExpiration(accessTokenExpiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .claim("auth", authorities)
                .claim("memberId", memberId)
                .setExpiration(refreshTokenExpiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenResponse.of(accessToken, refreshToken, BEARER_TYPE);
    }

    public Authentication getAuthentication(String accessToken, ServletRequest request) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.get("memberId",String.class), "", authorities);
        request.setAttribute("memberId", claims.get("memberId"));
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

//     토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_AUTH_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
    }

    // AccessToken 파싱하여 JWT Claims 추출
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
