package com.hostfully.demo.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * create ResponseMessage to keep them in MessageBucket
 * </p>
 *
 * @author recepb
 */

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class ResponseMessage {

    // TODO might add a few attribute such as "code"

    private String message;

    private ResponseMessageSeverity severity;

}
