package desafioInclude.projetoBack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()

        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessRuleException(BusinessRuleException ex){

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                HttpStatus.UNPROCESSABLE_CONTENT.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(errorResponseDTO);
    }

    @ExceptionHandler(desafioInclude.projetoBack.exception.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getName()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

}
