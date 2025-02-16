package cz.exodus.iam.exception;

import cz.exodus.jsend.network.exception.ErrorDetails;
import cz.exodus.jsend.network.exception.GlobalExceptionHandler;
import cz.exodus.jsend.network.rest.JSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.BindException;

@ControllerAdvice
@Slf4j
public class IAMExceptionHandler extends GlobalExceptionHandler {

    private final String COMPONENT_NAME = "exodus-iam";

    @ExceptionHandler(IAMException.class)
    public ResponseEntity<JSendResponse> handleExodusException(IAMException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), ex.getIamError().getCode(), ex.errorInstanceId(), COMPONENT_NAME);
        log.error("Exception: {}", ex.iamError);
        return ResponseEntity.status(ex.getIamError().getHttpStatus()).body(new JSendResponse(ex.getIamError().getErrorType().getJsendStatus(), errorDetails));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<JSendResponse> handleBindException(BindException e) {
        IAMException ex = new BadRequestException();
        log.error("Exception: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), ex.getIamError().getCode(), ex.errorInstanceId(), COMPONENT_NAME);
        return ResponseEntity.status(ex.getIamError().getHttpStatus()).body(new JSendResponse(ex.getIamError().getErrorType().getJsendStatus(), errorDetails));
    }
}

