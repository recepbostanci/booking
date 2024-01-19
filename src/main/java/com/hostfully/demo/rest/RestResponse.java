package com.hostfully.demo.rest;

import lombok.Getter;

import java.util.Collection;
import java.util.Objects;

/**
 * @author recepb
 */

@Getter
public class RestResponse<T> {

    private T data;

    private Collection<ResponseMessage> responseMessages;

    public RestResponse(T data) {
        this.data = data;
    }

    public static <T> RestResponse<T> of(T t) {
        return new RestResponse<>(t);
    }

    public static RestResponse<Void> empty() {
        return new RestResponse<>(null);
    }

    public void setResponseMessages(Collection<ResponseMessage> responseMessages) {
        if (Objects.nonNull(responseMessages)) {
            this.responseMessages = responseMessages;
        }
    }
}
