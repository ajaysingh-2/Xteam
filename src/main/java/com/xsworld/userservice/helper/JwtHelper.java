package com.xsworld.userservice.helper;

import com.xsworld.userservice.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

import static com.xsworld.userservice.constants.LoggerConstants.*;
import static com.xsworld.userservice.constants.MethodsNameConstants.*;

@Component
@Slf4j
public class JwtHelper {
    //requirement :

    private SecretKey key= Keys.hmacShaKeyFor(Constants.SECRET_KEY.getBytes());
    public static final long JWT_TOKEN_VALIDITY = (5 * 60 * 60);

    //    public static final long JWT_TOKEN_VALIDITY =  60;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .setExpiration(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, Constants.SECRET_KEY).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

//    public String generateTokenWithAuthentication(Authentication authentication) {
//        String jwt = Jwts.builder()
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .claim("email", authentication.getName())
//                .signWith(SignatureAlgorithm.HS512, Constants.SECRET_KEY).compact();
//        return jwt;
//    }

    public String generateToken(Authentication auth) {
        log.info(String.format(GENERATING_JWT,GENERATE_TOKEN,auth.getName()));
        String jwt=Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",auth.getName())
                .signWith(key)
                .compact();
        log.info(String.format(JWT_GENERATED,GENERATE_TOKEN,""));
        return jwt;
    }

    public String getEmailFromJwtToken(String jwt) {
        log.info(String.format(EXTRACTING_EMAIL_FROM_JWT,GET_EMAIL_FROM_JWT_TOKEN,""));
        jwt=jwt.substring(7);

        Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email=String.valueOf(claims.get("email"));

        log.info(String.format(EXTRACTED_EMAIL_FROM_JWT,GET_EMAIL_FROM_JWT_TOKEN,email));
        return email;
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        log.info(String.format(POPULATING_AUTHS_FROM_COLLECTIONS,POPULATE_AUTHORITIES,""));
        Set<String> auths=new HashSet<>();

        for(GrantedAuthority authority:collection) {
            auths.add(authority.getAuthority());
        }
        log.info(String.format(AUTHORITIES_POPULATED,POPULATE_AUTHORITIES,auths));
        return String.join(",",auths);
    }
}

