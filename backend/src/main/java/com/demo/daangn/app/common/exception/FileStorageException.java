package com.demo.daangn.app.common.exception;

public class FileStorageException extends RuntimeException {
    /*
     * 파일 관련 예외를 처리하는 클래스
     */

    public FileStorageException(String msg){
        super(msg);
    }

    public FileStorageException(String msg, Throwable cause){
        super(msg, cause);
    }

}
