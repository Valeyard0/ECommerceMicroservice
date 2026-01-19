package com.example.UserService.Jwt;
import com.example.UserService.Entity.User;
import com.example.UserService.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authHeader.substring(7);
            //String username = jwtService.extractUsername(token);
            Long userId = jwtService.extractUserIdFromToken(token);
            List<String> roles = jwtService.extractRoles(token);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> {
                            String authorityName = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                            return (GrantedAuthority) new SimpleGrantedAuthority(authorityName);
                        }).toList();
                System.out.println("AUTHORITIES:"+authorities);
                User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("HATA"));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                System.out.println("OBJEM:"+principal);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
