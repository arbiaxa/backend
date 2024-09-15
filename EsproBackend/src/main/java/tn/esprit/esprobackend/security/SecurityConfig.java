package tn.esprit.esprobackend.security;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import static tn.esprit.esprobackend.entities.Role.ADMIN;
import static tn.esprit.esprobackend.entities.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private final JWTAuthFilter jwtF;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityfilterChain (HttpSecurity http)throws Exception {
        System.out.println("Filtrage securite");
        return http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers("/auth/login","/auth/register","/resetpwd/**","configuration/ui","/configuration/security","/auth/updatemfa/{email}","/auth/verifyCode","/auth/enabled-count","/auth/disabled-count","/auth/positionss","/auth/notassaa").permitAll() // Permettre l'accès à /auth/login sans authentification
                                .requestMatchers("/user/**").hasAuthority("USER")// Permettre l'accès à /auth/login sans authentification

                                .requestMatchers("/user/retrieve-user/**").hasAuthority("USER")
                                .requestMatchers("/admin/****").hasAuthority("ADMIN")
                                .requestMatchers("/admin/disabled-count").hasAuthority("ADMIN")
                                .requestMatchers("/admin/groupingPosByUser").hasAnyAuthority("ADMIN")

                                .requestMatchers("/admin/notassa").hasAnyAuthority("ADMIN")
                                .requestMatchers("/admin/notassaa").hasAnyAuthority("ADMIN")

                                .requestMatchers("/pos/****").hasAuthority("ADMIN")


                                .anyRequest().authenticated()



                )
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtF, UsernamePasswordAuthenticationFilter.class)//kbal manchuf email w password mawjud, j execute token esite ou non ,user existe ou nn
               .exceptionHandling(
                      e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

                .build();

    }







}
