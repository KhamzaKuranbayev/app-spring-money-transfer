package uz.pdp.appspringmoneyconvertor.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.appspringmoneyconvertor.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    final JwtProvider jwtProvider;
    final AuthService authService;

    public JwtFilter(JwtProvider jwtProvider,
                     AuthService authService) {
        this.jwtProvider = jwtProvider;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);

            // TOKENNI VALIDATSIYADAN O'TKAZDIK
            boolean validateToken = jwtProvider.validateToken(token);
            if (validateToken) {

                // TOKEN DAN USERNAME NI OLDIK
                String usernameFromToken = jwtProvider.getUsernameFromToken(token);

                // USERNAME ORQALI USERDETAILSNI OLDIK
                UserDetails userDetails = authService.loadUserByUsername(usernameFromToken);

                // USERDETAILS ORQALI AUTHENTICATION YARATIB OLDIK
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // SYSTEMAGA QAYSI USER KIRGANLIGINI O'RNATIB QO'YDIK
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // O'ZIMIZNI FILTER IMIZNI BUGANIMIZDAN KEYIN BOSHQA FILTERLAR ISHLAB KETISHI KERAK
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
