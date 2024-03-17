package org.example.spartatodoapp.exception.custom;

public class NotAuthorityException extends RuntimeException{
    public NotAuthorityException() {
        super("권한이 없습니다.");
    }
}
