package com.example.NGGG.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    //400: 잘못된 정보 입력
    @ExceptionHandler({NotEnoughStocksException.class, WrongArgException.class, RuntimeException.class})
    public ResponseEntity<?> BadRequestException(final RuntimeException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    //401 Unauthorized
    @ExceptionHandler({UnAuthorizedException.class})
    public ResponseEntity<?> UnAuthorizedException(final UnAuthorizedException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    //403 Forbidden: 로그인은 됐는데 접근권한이 없음
    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<?> ForbiddenException(final ForbiddenException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    //404 Not Found: 요청 리소스를 찾을 수 없음
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> NotFoundException(final NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //409 Conflict: 아이디 중복
    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<?> ConflictException(final ConflictException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    //500 Internal Server Error
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleAll(final Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
