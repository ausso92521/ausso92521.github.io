package tech.leftii.animaltracker.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.WebUtils;
import tech.leftii.animaltracker.auth.CustomAuthenticationEntryPoint;
import tech.leftii.animaltracker.auth.FormLoginFailureHandler;
import tech.leftii.animaltracker.auth.FormLoginSuccessHandler;
import tech.leftii.animaltracker.auth.TokenService;
import tech.leftii.animaltracker.user.UserRepository;

import java.util.List;

/**
 * Configuration class for handling security (JWT, OAuth2ResourceServer, FormLogin, etc.)
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Allows us to use @PreAuth("hasAuthority('SCOPE_ROLE')") on our services to restrict endpoints
@AllArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository; // Injecting for use in CustomUserDetails bean
    private final TokenService tokenService; // Injecting so we can generate tokens on successful form authentications
    /**
     * Configuration for Spring Security
     *
     * @param http HttpSecurity object
     * @return HttpSecurity object configured for formLogin and jwt authorization
     * @throws Exception Catch all
     */
    @Bean
    public SecurityFilterChain formLoginSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .exceptionHandling().authenticationEntryPoint(authEntryPoint())
                .and()
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)  // Disables CSRF protection since we're using JWT
                .authorizeHttpRequests(auth ->
                        // Here we force form login on /api/v1/login endpoint and pass any other endpoint
                        // requests down the chain to be handled differently
                        // This configuration replaces the need for a separate controller class for the login endpoint
                        auth.requestMatchers("/api/v1/login").authenticated()
                                .anyRequest().permitAll()
                )
                // Configures form login properties
                .formLogin(form ->
                        form.loginProcessingUrl("/api/v1/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .successHandler(new FormLoginSuccessHandler(tokenService))
                                .failureHandler(new FormLoginFailureHandler())
                )
                // Setting stateless sessions since we use JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Catches all other requests and authorizes them using the JWT token
                .oauth2ResourceServer(oAuth ->
                        oAuth.jwt()
                                .and()
                                .bearerTokenResolver(this::tokenExtractor)
                )
                .build();
    }

    @Bean
    public CustomAuthenticationEntryPoint authEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    /**
     * Configures CORS so that requests received from frontend at http:localhost:3000 are allowed
     * Without this, the frontend will not be able to send requests or credential to the backend
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Frontend server:port
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        // Types of allowed request methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        // Types of headers allowed
        configuration.setAllowedHeaders(List.of("*"));
        // Allow credentials to be passed (Bearer token)
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Match all patterns
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Extracts token "jwt_token" from request cookies and returns the jwt value for processing by oauth2ResourceServer
     *
     * @param request Used to extract cookie
     * @return jwt if 'jwt_token' cookie is present in request, otherwise returns null
     */
    public String tokenExtractor(HttpServletRequest request) {
        Cookie tokenCookie = WebUtils.getCookie(request, "jwt_token");
        if (tokenCookie != null) {
            System.out.println(tokenCookie.getValue());
            return tokenCookie.getValue();
        }
        return null;
    }

    /**
     * Creates bean to use our custom UserDetailsService
     *
     * @return CustomUserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    /*
     *
     * */

    /**
     * Creates PasswordEncoder bean so that passwords are encrypted using bcrypt for more secure storage
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Creates ServletWebServerFactory bean used to configure tomcat servlet to forward all traffic to https:8443
     *
     * @return TomcatServletWebServerFactory with configurations to forward all requests to https:8443
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        // Uses connector to forward requests from http:8080 to https:443
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    /**
     * Used by ServletWebFactory bean to configure connector to forward traffic to https:443
     *
     * @return Connector configured to forward all http:8080 traffic to https:443
     */
    private Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(443);
        return connector;
    }
}
