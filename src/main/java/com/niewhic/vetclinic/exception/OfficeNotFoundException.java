package com.niewhic.vetclinic.exception;

public class OfficeNotFoundException extends RuntimeException{
    public OfficeNotFoundException(String message) {
        super(message);
    }
}
