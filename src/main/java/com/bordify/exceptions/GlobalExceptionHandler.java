package com.bordify.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler class to handle various types of exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler  {



//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
//        return new ErrorResponse(true, ex.getMessage());
//    }
//

    /**
     * Handles Throwable exceptions.
     *
     * @param throwable The Throwable object representing the exception.
     * @return ApiExceptionResponse containing details of the exception.
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiExceptionResponse handleThrowable(Throwable throwable) {
        // Obtener el nombre de la clase donde se originó la excepción
        String className = throwable.getStackTrace()[0].getClassName();
        // Loguear o imprimir el nombre de la clase y el mensaje de error
        System.err.println("Error en la clase: " + className);
        System.err.println("Mensaje de error: " + throwable.getMessage());

        return new ApiExceptionResponse.ApiExceptionResponseBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(throwable.getMessage())
                .build();
    }

    /**
     * Handles DuplicateEmailException.
     *
     * @param ex The DuplicateEmailException object.
     * @return ApiExceptionResponse containing details of the exception.
     */
    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiExceptionResponse handleDuplicateEmailException(DuplicateEmailException ex) {
        return new ApiExceptionResponse.ApiExceptionResponseBuilder()
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    /**
     * Handles InvalidRequestArgumentException.
     *
     * @param ex The InvalidRequestArgumentException object.
     * @return ApiExceptionResponse containing details of the exception.
     */
    @ExceptionHandler(InvalidRequestArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiExceptionResponse  handleInvalidRequestArgumentException(InvalidRequestArgumentException ex) {
        return new ApiExceptionResponse.ApiExceptionResponseBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }


    /**
     * Handles UserNotFoundException.
     *
     * @param ex The UserNotFoundException object.
     * @return ApiExceptionResponse containing details of the exception.
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiExceptionResponse handleUsernameNotFoundException(UserNotFoundException ex) {
        return new ApiExceptionResponse.ApiExceptionResponseBuilder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    /**
     * Handles JwtTokenException.
     *
     * @param ex The JwtTokenException object.
     * @return ApiExceptionResponse containing details of the exception.
     */
    @ExceptionHandler(JwtTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiExceptionResponse handleJwtTokenException(JwtTokenException ex) {
        return new ApiExceptionResponse.ApiExceptionResponseBuilder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }


    /**
     * Handles ResourceNotCreatedException.
     *
     * @param ex The ResourceNotCreatedException object.
     * @return ApiExceptionResponse containing details of the exception.
     */
    @ExceptionHandler(ResourceNotCreatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiExceptionResponse handleResourceNotCreatedException(ResourceNotCreatedException ex) {
        return new ApiExceptionResponse.ApiExceptionResponseBuilder()
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }


    /**
     * Handles EntityNotFound.
     *
     * @param ex The EntityNotFound object.
     * @return ApiExceptionResponse containing details of the exception.
     */
    @ExceptionHandler(EntityNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiExceptionResponse handleBoardNotFound(EntityNotFound ex) {
        return new ApiExceptionResponse.ApiExceptionResponseBuilder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }
}
