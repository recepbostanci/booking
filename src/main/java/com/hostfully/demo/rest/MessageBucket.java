package com.hostfully.demo.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

/**
 * <p>
 * keep messages at RequestContext per request and manage them.
 * </p>
 *
 * @author recepb
 */

@Component
public class MessageBucket {

    private static final String RESPONSE_MESSAGE_BUCKET = "ResponseMessageBucket";

    public void addMessage(String message, ResponseMessageSeverity responseMessageSeverity) {
        final ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage(message);
        responseMessage.setSeverity(responseMessageSeverity);
        final Collection<ResponseMessage> messages = getMessages();
        messages.add(responseMessage);

        final RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        requestAttributes.setAttribute(RESPONSE_MESSAGE_BUCKET, messages, RequestAttributes.SCOPE_REQUEST);
    }

    public Collection<ResponseMessage> getMessages() {
        final RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        final Collection<ResponseMessage> bucket = getBucketFromRequestAttr(requestAttributes);
        requestAttributes.setAttribute(RESPONSE_MESSAGE_BUCKET, bucket, RequestAttributes.SCOPE_REQUEST);

        return bucket;
    }

    private Collection<ResponseMessage> getBucketFromRequestAttr(RequestAttributes requestAttributes) {
        final Collection<ResponseMessage> bucket = (Collection<ResponseMessage>) requestAttributes.getAttribute(RESPONSE_MESSAGE_BUCKET, RequestAttributes.SCOPE_REQUEST);
        if (Objects.isNull(bucket)) {
            return Collections.newSetFromMap(new HashMap<>());
        }
        return bucket;
    }

    public boolean hasErrors() {
        return getMessages().stream().anyMatch(responseMessage -> ResponseMessageSeverity.ERROR.equals(responseMessage.getSeverity()));
    }
}
