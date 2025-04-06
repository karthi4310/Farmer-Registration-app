package com.farmer.farmermanagement.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	// 1. Handle DTO validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		log.warn("Validation Exception: {}", ex.getMessage());

		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			String field = error.getField();
			String message = error.getDefaultMessage();
			errors.put(field, String.format("Invalid value for '%s': %s", field, message));
		}

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	// 2. Handle validation errors on @RequestParam / @PathVariable / @RequestHeader
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
		log.warn("Constraint Violation: {}", ex.getMessage());

		Map<String, String> errors = new HashMap<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String path = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			errors.put(path, String.format("Invalid input: %s", message));
		}

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	// 3. Handle invalid types in path/query parameters
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String paramName = ex.getName();
		String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "expected type";
		String value = ex.getValue() != null ? ex.getValue().toString() : "null";

		String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s.", value, paramName,
				requiredType);
		Map<String, String> error = new HashMap<>();
		error.put("error", message);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// 4. Custom NotFound exceptions
	@ExceptionHandler({ FarmerNotFoundException.class, CropNotFoundException.class,
			LandDetailsNotFoundException.class })
	public ResponseEntity<Map<String, String>> handleNotFoundException(RuntimeException ex) {
		log.warn("Not Found Exception: {}", ex.getMessage());

		Map<String, String> error = new HashMap<>();
		error.put("error", "Resource not found");
		error.put("message", ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	// 5. Custom Invalid data exception
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Map<String, String>> handleInvalidDataException(InvalidDataException ex) {
		log.warn("Invalid Data Exception: {}", ex.getMessage());

		Map<String, String> error = new HashMap<>();
		error.put("error", "Invalid data");
		error.put("message", ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// 6. Database constraint violations (e.g., duplicate keys, foreign key
	// constraints)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex) {
		log.error("Database Error: {}", ex.getMessage());

		Map<String, String> error = new HashMap<>();
		error.put("error", "Database error");
		error.put("message",
				"Your request could not be completed due to a database constraint. Please ensure your data is valid and try again."
						+ ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	// 7. Fallback for unexpected errors
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
		log.error("Unexpected Exception: {}", ex.getMessage(), ex);

		Map<String, String> error = new HashMap<>();
		error.put("error", "Internal server error");
		error.put("message", "Something went wrong on our end. Please try again later.");

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
