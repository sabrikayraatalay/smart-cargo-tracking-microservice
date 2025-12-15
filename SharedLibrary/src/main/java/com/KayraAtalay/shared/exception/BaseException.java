package com.KayraAtalay.shared.exception;

public class BaseException extends RuntimeException {

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.prepareErrorMessage());
    }

}