package tech.leftii.animaltracker.shared;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exists only to enable custom exceptions to be handled in responses when thrown.
 * This means that when a custom exception like NoteNotFoundException is thrown, the exception with ProblemDetails
 *  will be sent in the response to the client
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler { }
