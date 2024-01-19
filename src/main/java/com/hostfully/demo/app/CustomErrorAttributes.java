package com.hostfully.demo.app;

import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rest.ResponseMessageSeverity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * handle all threw exception to pass more meaningfull and readable errors to clients
 * </p>
 *
 * @author recepb
 */

@Component
@RequiredArgsConstructor
class CustomErrorAttributes extends DefaultErrorAttributes {

    private final MessageBucket messageBucket;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

        final Throwable error = super.getError(webRequest);

        // extensible for various exceptions. I write for Input validators
        if (error instanceof MethodArgumentNotValidException) {
            final MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) error;
            argumentNotValidException.getBindingResult().getAllErrors().forEach(objectError -> {
                messageBucket.addMessage(objectError.getDefaultMessage(), ResponseMessageSeverity.ERROR);
            });
        }

        if (Objects.nonNull(error)) {
            final String errorMessage = error.getMessage();
            if (Objects.nonNull(errorMessage)) {
                messageBucket.addMessage(errorMessage, ResponseMessageSeverity.ERROR);
            }
        }

        if (!messageBucket.hasErrors()) {
            messageBucket.addMessage("An unexpected error occured!", ResponseMessageSeverity.ERROR);
        }

        final Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        errorAttributes.put("responseMessages", messageBucket.getMessages());

        return errorAttributes;
    }
}
