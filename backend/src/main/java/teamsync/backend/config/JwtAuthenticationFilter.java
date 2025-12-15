package teamsync.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import teamsync.backend.entity.User;
import teamsync.backend.repository.user.UserRepository;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {

        String path = req.getRequestURI();
        String method = req.getMethod();

        if (path.startsWith("/api/auth/") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs/") ||
                path.startsWith("/health") ||
                path.equals("/") ||
                path.startsWith("/ws/") ||
                path.startsWith("/api/user/") ||
                (path.startsWith("/api/calenderevent/") && "GET".equals(method))) {

            filterChain.doFilter(req, res);
            return;
        }

        String token = resolveToken(req);

        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 블랙리스트 토큰인지 확인
            if (redisTemplate.opsForValue().get("blacklist:" + token) != null) {
                filterChain.doFilter(req, res);
                return;
            }

            String userId = jwtTokenProvider.getUserId(token);

            User domainUser = userRepository.findById(userId).orElse(null);

            if (domainUser != null) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                domainUser,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(domainUser.getRole().name()))
                        );

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(req, res);
    }

    private String resolveToken(HttpServletRequest req) {
        String bearer = req.getHeader("Authorization");

        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
