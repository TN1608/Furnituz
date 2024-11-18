package java6.com.config;

import java6.com.dao.UserDAO;
import java6.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig {
//    @Autowired
//    UserDAO dao;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //CSRF va CORS
        http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable());

        //Cau hinh bao mat
        http.authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
        )
                .exceptionHandling(e -> e
                .accessDeniedPage("/error/403")
        );
        return http.build();
//        http.authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/product/**").authenticated()
//                        .requestMatchers("/**","/error/**","/account/").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/account/login")
//                        .defaultSuccessUrl("/home" ,false)
//                        .failureUrl("/account/login")
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/account/logout")
//                        .logoutSuccessUrl("/home")
//                        .permitAll()
//                )
//        return http.build();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(username -> {
//            User user = dao.findById(username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//            // Set roles and authorities
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole())); // Role as "ROLE_ADMIN", "ROLE_USER"
//
//            // Return Spring Security UserDetails
//            return new org.springframework.security.core.userdetails.User(
//                    user.getUsername(),
//                    user.getPassword(),
//                    authorities
//            );
//        }).passwordEncoder(passwordEncoder());
//    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            User user = dao.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//            return new org.springframework.security.core.userdetails.User(
//                    user.getUsername(),
//                    user.getPassword(),
//                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())) // Role as "ROLE_USER", "ROLE_ADMIN"
//            );
//        };
//    }
}
