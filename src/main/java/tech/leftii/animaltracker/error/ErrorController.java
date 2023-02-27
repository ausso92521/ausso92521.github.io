package tech.leftii.animaltracker.error;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used to return descriptions of why an error might be thrown.
 * The GetMapping for each should correlate to problemDetail.Type of teh exception thrown
 */
@RestController
@RequestMapping("/api/v1/errors")
public class ErrorController {

    /**
     * Maps to the error endpoint used on each NotFoundException
     *
     * @return String explaining the error to the end user
     */
    @GetMapping({"/not-found", "/not-found/"})
    public String getResourceNotFoundDescription(){
        return "This error occurs when an item is requested that either does not exist or may have been moved.";
    }

    /**
     * Maps to the error endpoint used on IncorrectPasswordException
     *
     * @return String explaining the error to the end user
     */
    @GetMapping({"/incorrect-password", "/incorrect-password/"})
    public String getIncorrectPasswordDescription(){
        return """
                This error occurs when a user attempt to authenticate using the wrong password. This can happen
                when logging in, or in any other area when confirming your password is necessary, e.g. updating account.
                """;
    }

    /**
     * Maps to the error endpoint used on LoginFailedException
     *
     * @return String explaining the error to the end user
     */
    @GetMapping({"/login-failed", "/login-failed/"})
    public String getLoginFailed(){
        return """
                This error indicates the login credentials used were unable to be authenticated.
                Check your username and password and try again.
                """;
    }
}
