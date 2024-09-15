package tn.esprit.esprobackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.esprit.esprobackend.repositories.userRepo;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Service
@RequiredArgsConstructor

public class JWTAuthFilter extends OncePerRequestFilter {

private final UserDetailsService userDetailsService;
    private final JwtService jwtService ;




/*userRepo userepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    */
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {
//si
        if (request.getServletPath().contains("/auth/**") || request.getServletPath().contains("/resetpwd/**")) {

            filterChain.doFilter(request, response); //methode pour effectuer une chine de filtre pour le request et response
            return;

        }
        //  SecurityContext securityContext=SecurityContextHolder.createEmptyContext() ;
        else {
            String authheader = request.getHeader("Authorization");

            System.out.println("heloo");
            if (authheader == null || !authheader.startsWith("Bearer")) {
                System.out.println("arbia");
                filterChain.doFilter(request, response); //methode pour effectuer une chine de filtre pour le request et response
                return;
            }

            //extraire seulement le token
            String jwt = authheader.substring(7);
            String userMail = jwtService.extractUsername(jwt); //extraire email from the token passed in request
            System.out.println(userMail);

            if (userMail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("sassi");
                UserDetails userDetails = userDetailsService.loadUserByUsername(userMail);
                //if (userDetails == null) System.out.println("user est null");
                //else {

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("validated Token");
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );


                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );


                    securityContext.setAuthentication(authToken);
                    SecurityContextHolder.setContext(securityContext);

                    // Debugging prints
                    System.out.println(authToken.getPrincipal());
                    System.out.println(authToken.isAuthenticated());
                    System.out.println(securityContext.getAuthentication());


                    //  System.out.println(authToken.getPrincipal());
                    //System.out.println(authToken.isAuthenticated());
                    // SecurityContextHolder.getContext().setAuthentication(authToken);
                    //SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                }

            }
            filterChain.doFilter(request, response);
        }
    }
}



