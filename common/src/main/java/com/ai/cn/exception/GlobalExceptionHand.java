package com.ai.cn.exception;


import com.nft.cn.util.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;

/**
 *
 */
@EnableWebMvc
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHand {

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String msg = "       ";
        log.error(msg, e);
        return BaseResult.fail(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = e.getMessage();
        log.error("       ", e);
        return BaseResult.fail(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = handleBindingResult(e.getBindingResult());
        log.error("      : ", e);
        return BaseResult.fail(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public BaseResult handleBindException(BindException e) {
        String msg = handleBindingResult(e.getBindingResult());
        log.error("      :", e);
        return BaseResult.fail(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResult handleServiceException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String msg = violations.iterator().next().getMessage();
        log.error("      :", e);
        return BaseResult.fail(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public BaseResult handleValidationException(ValidationException e) {
        String msg = e.getMessage();
        log.error("       ", e);
        return BaseResult.fail(msg);
    }

    /**
     * 401 - Unauthorized
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(LoginException.class)
    public BaseResult handleLoginException(LoginException e) {
        String msg = e.getMessage();
        log.error("     ", e);
        return BaseResult.unAuth(msg);
    }



    /**
     * 403 - Unauthorized
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public BaseResult handleLoginException(UnauthorizedException e) {
        String msg = e.getMessage();
        log.error("      ", e);

        return BaseResult.unAuth(e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = "          ";
        log.error(msg, e);
        return BaseResult.fail(msg);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResult handleHttpMediaTypeNotSupportedException(Exception e) {
        String msg = "          ";
        log.error(msg, e);
        return BaseResult.fail(msg);
    }

    /**
     * 422 - UNPROCESSABLE_ENTITY
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public BaseResult handleMaxUploadSizeExceededException(Exception e) {
        String msg = "                   ";
        log.error(msg, e);
        return BaseResult.fail(msg);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(VoException.class)
    public BaseResult handleVoException(VoException e){
        String msg = e.getMessage();
        log.error(msg, e);
        return BaseResult.fail(msg);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e) {
        String msg = "       ,     ";
        log.error(msg, e);
        return BaseResult.fail(msg);
    }

    /**
     *                      。
     * <p>
     *     leigq <br>
     *      2017 10 16    9:09:22 <br>
     * <p>
     *      <br>
     *       <br>
     *       <br>
     * </p>
     *
     * @param result
     */
    private String handleBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            final List<FieldError> fieldErrors = result.getFieldErrors();
            return fieldErrors.iterator().next().getDefaultMessage();
        }
        return null;
    }
}
