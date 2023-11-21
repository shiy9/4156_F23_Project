package com.ims.exception;

import com.ims.constants.ClientConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Currently handles all AccessDeniedException thrown globally (for this project, they will be
 * from the @PreAuthorize failures from different Item-related and Order-related requests.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public String handleAccessDeniedException(AccessDeniedException ex) {
    return ClientConstants.TYPE_NOT_AUTHORIZED;
  }
}
