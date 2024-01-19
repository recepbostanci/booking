package com.hostfully.demo.booking;

import com.hostfully.demo.input_output.BookingBaseInput;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rest.ResponseMessageSeverity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * <p>
 * In validators, we can provide pre-validations without business informations
 * They could be efficient for search services, date comparison etc.
 *
 * @author recepb
 */

@Component
@RequiredArgsConstructor
class BookingBaseInputValidator implements Validator {

    private final MessageBucket messageBucket;

    @Override
    public boolean supports(Class<?> clazz) {
        return BookingBaseInput.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final BookingBaseInput bookingBaseInput = (BookingBaseInput) target;

        final LocalDate startDate = bookingBaseInput.getStartDate();
        final LocalDate endDate = bookingBaseInput.getEndDate();

        if (Objects.nonNull(startDate) && Objects.nonNull(endDate) && ChronoUnit.DAYS.between(startDate, endDate)< 1) {
            errors.rejectValue("startDate", "startDateEndDate", "EndDate must be at least 1 day ahead");
            messageBucket.addMessage("StartDate must be earlier than EndDate", ResponseMessageSeverity.ERROR);
        }

    }
}
