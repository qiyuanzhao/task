package com.lavector.crawlers.tasks.utlis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static ResponseEntity buildSuccess() {
        return new ResponseEntity(HttpStatus.OK);
    }

    public static <T>ResponseEntity <T> buildSuccess(T data) {
        return new ResponseEntity<T>(data, HttpStatus.OK);
    }

    public static ResponseEntity buildSuccess(String data) {
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> buildSuccess(T data, HttpStatus code) {
        return new ResponseEntity<T>(data, code);
    }

    public static ResponseEntity buildSuccess(String data, HttpStatus code) {
        return new ResponseEntity<>(data, code);
    }


    public static ResponseEntity buildError() {
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity buildError(T data) {
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity buildError(String data) {
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity buildError(T data, HttpStatus code) {
        return new ResponseEntity<>(data, code);
    }

    public static ResponseEntity buildError(String data, HttpStatus code) {
        return new ResponseEntity<>(data, code);
    }

}