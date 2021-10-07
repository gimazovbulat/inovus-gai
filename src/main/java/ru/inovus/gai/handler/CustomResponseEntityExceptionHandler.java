package ru.inovus.gai.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.inovus.gai.exception.GaiRuntimeException;
import ru.inovus.gai.model.ErrorDetailsDto;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GaiRuntimeException.class)
    @ResponseBody
    public ResponseEntity<Object> handleFeignException(
            GaiRuntimeException ex,
            WebRequest request
    ) {
        log.error(ex.getMessage());
        ErrorDetailsDto error = new ErrorDetailsDto(
                request,
                HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage()
        );
        return handleExceptionInternal(ex, error, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request);
    }
}