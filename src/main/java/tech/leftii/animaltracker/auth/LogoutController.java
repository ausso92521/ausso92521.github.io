package tech.leftii.animaltracker.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class exposes the /api/v1/logout endpoint for a user to logout
 * Uses DELETE request
 */

@RestController
@RequestMapping("/api/v1/logout")
@AllArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    /**
     * Calls service to expire jwt_token cookie for logout
     * @param response Response object that is used to set jwt_token cookie
     */
    @DeleteMapping({"","/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logoutUser(HttpServletResponse response){
        logoutService.logout(response);
    }
}
