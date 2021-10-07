package ru.inovus.gai.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@NoArgsConstructor
@Getter
public class ErrorDetailsDto {

    /**
     * Http статус
     */
    private Integer status;

    /**
     * Код ошибки
     */
    private String error;

    /**
     * Сообщение
     */
    private String message;

    /**
     * Путь запроса
     */
    private String path;

    /**
     * @param request      ошибочный запрос
     * @param status       статус ошибки
     * @param errorMessage сообщения
     */
    public ErrorDetailsDto(WebRequest request, HttpStatus status, String errorMessage) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = errorMessage;
        this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}
