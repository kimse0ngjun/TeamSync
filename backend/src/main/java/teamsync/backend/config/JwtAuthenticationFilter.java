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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

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

            // 블랙리스트 체크 (로그아웃 처리된 AccessToken)
            String isBlacklisted = (String) redisTemplate.opsForValue().get("blacklist:" + token);
            if (isBlacklisted != null) {
                filterChain.doFilter(req, res);
                return;
            }

            String userId = jwtTokenProvider.getUserId(token);
            String email = jwtTokenProvider.getEmail(token);

            UserDetails userDetails = createUserDetailsFromToken(userId, email);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(req, res);
    }

    // UserDetails 객체 생성
    private UserDetails createUserDetailsFromToken(String userId, String email) {

        return new User(
                email,
                "",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    // Authorization 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest req) {
        String bearer = req.getHeader("Authorization");

        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
