package tech.leftii.animaltracker.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class used solely to logout user
 * When logout() is called, jwt_token cookie is forced to expire so auth
 * is no longer sent in cookie in subsequent requests
 */
@Service
@Slf4j
public class LogoutService {

    /**
     * No return value, resets jwt_token cookie value to null and expires the cookie
     * This removes the httponly cookie from the client's browser, so it is no longer submitted in
     * subsequent requests. User will need to perform login again to access protected resources
     * @param response Response object allows editing and setting cookies
     */
    void logout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setPath("/api/v1");
        response.addCookie(cookie);
        log.info("jwt_token expired. User logged out.");
    }
}
