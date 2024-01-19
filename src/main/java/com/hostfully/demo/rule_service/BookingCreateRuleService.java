package com.hostfully.demo.rule_service;

import com.hostfully.demo.booking.Booking;
import com.hostfully.demo.booking.BookingRepository;
import com.hostfully.demo.input_output.BookingCreateInput;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rest.ResponseMessageSeverity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author recepb
 */
@Service
@RequiredArgsConstructor
class BookingCreateRuleService implements RuleService<BookingCreateInput> {

    private final MessageBucket messageBucket;

    private final BookingRepository bookingRepository;

    private final BlockValidationService blockValidationService;

    @Override
    public void apply(BookingCreateInput bookingCreateInput) {
        final Long propertyId = bookingCreateInput.getPropertyId();
        final LocalDate startDate = bookingCreateInput.getStartDate();
        final LocalDate endDate = bookingCreateInput.getEndDate();

        blockValidationService.validateDatesAreAvailable(propertyId, startDate, endDate);

        checkBookingExistence(bookingCreateInput);
    }

    private void checkBookingExistence(BookingCreateInput bookingCreateInput) {
        final Optional<Booking> bookingOptional = bookingRepository.findActiveBookingsByPropertyIdAndStartDateAndEndDate(
                bookingCreateInput.getPropertyId(), bookingCreateInput.getStartDate(), bookingCreateInput.getEndDate());

        if (!bookingOptional.isPresent()) {
            return;
        }

        messageBucket.addMessage("Dates are not available for this property for a new booking", ResponseMessageSeverity.ERROR);
    }
}
