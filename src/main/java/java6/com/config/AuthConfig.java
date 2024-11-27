package java6.com.config;

import com.nimbusds.oauth2.sdk.AuthorizationRequest;
import java6.com.dao.UserDAO;
import java6.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig {
    @Autowired
    UserDAO dao;
    @Autowired
    private AuthenticationConfiguration auth;

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
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/product/**").authenticated()
                        .requestMatchers("/**", "/error/**", "/account/").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/account/login/form")
                        .defaultSuccessUrl("/account/login/success", true)
                        .failureUrl("/account/login")
                )
                .logout(logout -> logout
                        .logoutUrl("/account/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll()
                )
                .exceptionHandling(e -> e
                        .accessDeniedPage("/error/403")
                );
        http.oauth2Login(auth -> auth
                .loginPage("/account/login/form")
                .authorizationEndpoint(oa -> oa
                        .baseUri("/account/login/authorize")
                        .authorizationRequestRepository(getRepository())
                )
                .tokenEndpoint(te -> te
                        .accessTokenResponseClient(getToken())
                )
                .defaultSuccessUrl("/account/login/auth/success", true)
                .failureUrl("/account/login")
        );
        return http.build();
    }
     @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository() {
         return new HttpSessionOAuth2AuthorizationRequestRepository();
     }
     @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken() {
        return new DefaultAuthorizationCodeTokenResponseClient();
     }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return auth.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = dao.findById(username).orElseThrow(() ->
                    new UsernameNotFoundException("Tài khoản không tồn tại"));
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        };
    }
}
