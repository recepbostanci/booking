package com.hostfully.demo.rest;

import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>
 * handle all restcontroller response and add them messagebucket messages
 * </p>
 *
 * @author recepb
 */

@Aspect
@Component
@AllArgsConstructor
public class ResponseAspect {

    private final MessageBucket messageBucket;

    @Pointcut("execution(* (@org.springframework.web.bind.annotation.RestController *).*(..))")
    private void controller() {

    }

    @AfterReturning(pointcut = "controller()", returning = "retVal")
    private void getResponse(ResponseEntity<?> retVal) {

        if (Objects.nonNull(retVal) && retVal.hasBody()) {

            if (!(retVal.getBody() instanceof RestResponse)) {
                return;
            }

            final RestResponse<?> restResponse = (RestResponse<?>) retVal.getBody();
            restResponse.setResponseMessages(messageBucket.getMessages());

        }

    }
}
