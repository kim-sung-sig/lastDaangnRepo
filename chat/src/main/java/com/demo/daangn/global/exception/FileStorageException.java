package com.demo.daangn.global.exception;

/*
 * 파일을 다루는 중 발생하는 예외를 처리하는 클래스
 */
public class FileStorageException extends Exception {

    public FileStorageException(String msg){
        super(msg);
    }

    public FileStorageException(String msg, Throwable cause){
        super(msg, cause);
    }

}
