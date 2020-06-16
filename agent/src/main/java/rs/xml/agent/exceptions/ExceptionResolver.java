package rs.xml.agent.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {
	
	@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>(exception.getMessage(), headers, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(UniqueConstrainException.class)
    public ResponseEntity<?> uniqueConstrainException(UniqueConstrainException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>(exception.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ServiceNotAvailable.class)
    public ResponseEntity<?> ServiceNotAwailable(ServiceNotAvailable exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>(exception.getMessage(), headers, HttpStatus.SERVICE_UNAVAILABLE);
    }
	
}
