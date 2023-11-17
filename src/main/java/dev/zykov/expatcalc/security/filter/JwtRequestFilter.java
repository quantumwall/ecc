package dev.zykov.expatcalc.security.filter;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.zykov.expatcalc.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        String jwt = null;
        String email = null;

        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                email = jwtUtil.geEmail(jwt);
            } catch (ExpiredJwtException e) {

            } catch (SignatureException e) {

            }
        }

        if (Objects.nonNull(email) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            var roles = jwtUtil.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
            var token = new UsernamePasswordAuthenticationToken(email, null, roles);
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);

    }

}
