package tech.leftii.animaltracker.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Handles login form sent to /api/v1/login
 * Uses TokenService to generate JWT token after authenticating and sets http-only cookie in HttpServletResponse
 */
public class FormLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;

    public FormLoginSuccessHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Creates http-only cookie and adds it to the response
     *
     * @param request  the request which caused the successful authentication
     * @param response the response
     * @param auth     the <tt>Authentication</tt> object which was created during
     *                 the authentication process.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        String jwt = tokenService.generateToken(auth);
        Cookie jwtCookie = new Cookie("jwt_token", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setMaxAge(60 * 30);   // 30 min expiration
        jwtCookie.setPath("/api/v1");
        jwtCookie.setAttribute("SameSite", "None");
        response.addCookie(jwtCookie);
    }
}
