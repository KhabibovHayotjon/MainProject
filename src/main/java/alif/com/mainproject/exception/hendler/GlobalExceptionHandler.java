package alif.com.mainproject.exception.hendler;

import alif.com.mainproject.exception.AppLoginException;
import alif.com.mainproject.exception.RegisterException;
import alif.com.mainproject.exception.UserResourceNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(RegisterException.class)
//    private ResponseEntity<?> registerException(RegisterException registerException){
//        return ResponseEntity.status(409).body(registerException.getMessage());
//    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Object requestArgumentException(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler(AppLoginException.class)
    public ResponseEntity<?> appLoginException(AppLoginException exception){
        return ResponseEntity.status(404).body(exception.getMessage());
    }

    @ExceptionHandler(UserResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(UserResourceNotFoundException exception){
        return ResponseEntity.status(409).body(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> access(AccessDeniedException exception){
        return ResponseEntity.status(409).body(exception.getMessage());
    }

}
