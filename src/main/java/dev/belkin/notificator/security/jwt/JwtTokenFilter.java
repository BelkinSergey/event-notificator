package dev.belkin.notificator.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {


    private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtTokenManager jwtTokenManager;

    public JwtTokenFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        var jwtToken = authorizationHeader.substring(7);

        Integer userId;
        String role;

        try {
            userId = jwtTokenManager.getIdFromToken(jwtToken);
            if (userId == null) {
                log.error("некорректный id пользователя");
                filterChain.doFilter(request, response);
                return;
            }

            role = jwtTokenManager.getRoleFromToken(jwtToken);

            if (role == null) {
                log.error("роль пользователя не найдена");
                filterChain.doFilter(request, response);
                return;
            }

        } catch (Exception e) {
            log.error("ошибка при чтении токена", e);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("userId {}, role {}",userId, role);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                List.of(new SimpleGrantedAuthority(role))
        );
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);


    }

}
