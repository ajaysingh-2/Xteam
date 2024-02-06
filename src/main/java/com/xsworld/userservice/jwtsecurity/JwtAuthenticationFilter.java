package com.xsworld.userservice.jwtsecurity;
import com.xsworld.userservice.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import static com.xsworld.userservice.constants.LoggerConstants.*;
import static com.xsworld.userservice.constants.MethodsNameConstants.DO_FILTER_INTERNAL;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(Constants.JWT_HEADER);
        log.info("jwt ------ "+jwt);
        if(jwt!=null) {
            jwt=jwt.substring(7);
            log.info("jwt ------ "+jwt);
            try {

                SecretKey key= Keys.hmacShaKeyFor(Constants.SECRET_KEY.getBytes());

                Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String email=String.valueOf(claims.get("email"));

                String authorities=String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication athentication=new UsernamePasswordAuthenticationToken(email,null, auths);

                SecurityContextHolder.getContext().setAuthentication(athentication);

            }  catch (ExpiredJwtException e) {
                // Token has expired
                log.error(String.format(TOKEN_HAS_EXPIRED,DO_FILTER_INTERNAL,e.getMessage()));
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                return;
            } catch (MalformedJwtException e) {
                // Invalid token format
                log.error(String.format(INVALID_TOKEN_FORMAT,DO_FILTER_INTERNAL,e.getMessage()));
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token format");
                return;
            } catch (Exception e) {
                // Other token-related errors
                log.error(String.format(INVALID_TOKEN,DO_FILTER_INTERNAL,e.getMessage()));
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}

