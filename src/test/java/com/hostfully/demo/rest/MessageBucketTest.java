package com.hostfully.demo.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class MessageBucketTest {

    private static final String RESPONSE_MESSAGE_BUCKET = "ResponseMessageBucket";

    @InjectMocks
    private MessageBucket messageBucket;

    @BeforeEach
    public void setUp() {

        messageBucket = new MessageBucket();

        final MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void add_message_should_add_to_request_context() {

        RequestContextHolder.getRequestAttributes()
                .setAttribute(RESPONSE_MESSAGE_BUCKET, null, RequestAttributes.SCOPE_REQUEST);

        messageBucket.addMessage("message", ResponseMessageSeverity.ERROR);

        final Collection responseMessageBucket = (Collection) RequestContextHolder.getRequestAttributes()
                .getAttribute(RESPONSE_MESSAGE_BUCKET, RequestAttributes.SCOPE_REQUEST);

        assertEquals(responseMessageBucket.size(), 1);

    }

    @Test
    void get_messages_should_initialize_list() {

        RequestContextHolder.getRequestAttributes()
                .setAttribute(RESPONSE_MESSAGE_BUCKET, null, RequestAttributes.SCOPE_REQUEST);

        final Collection<ResponseMessage> messages = messageBucket.getMessages();

        assertNotNull(messages);
    }

    @Test
    void has_errors_should_care_request_context_messages() {

        RequestContextHolder.getRequestAttributes()
                .setAttribute(RESPONSE_MESSAGE_BUCKET, null, RequestAttributes.SCOPE_REQUEST);

        messageBucket.addMessage("message", ResponseMessageSeverity.ERROR);

        assertTrue(messageBucket.hasErrors());

    }
}