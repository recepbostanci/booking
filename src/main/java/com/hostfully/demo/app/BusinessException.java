package com.hostfully.demo.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>
 * Custom exception for business rules
 * </p>
 *
 * @author recepb
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BusinessException extends RuntimeException {

    public BusinessException() {

    }

    BusinessException(Exception e) {
        super(e.getMessage(), e);
    }

}
