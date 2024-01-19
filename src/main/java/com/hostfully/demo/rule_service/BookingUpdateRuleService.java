package com.hostfully.demo.rule_service;

import com.hostfully.demo.booking.Booking;
import com.hostfully.demo.booking.BookingRepository;
import com.hostfully.demo.booking.BookingStatus;
import com.hostfully.demo.input_output.BookingUpdateInput;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rest.ResponseMessageSeverity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * <p>
 *     we didn't check booking belongs to guest. it could be possible after getting from security context.
 * </p>
 *
 *  @author recepb
 */
@Service
@RequiredArgsConstructor
class BookingUpdateRuleService implements RuleService<BookingUpdateInput> {

    private final MessageBucket messageBucket;

    private final BlockValidationService blockValidationService;

    private final BookingRepository bookingRepository;

    @Override
    public void apply(BookingUpdateInput bookingUpdateInput) {

        final Optional<Booking> bookingOptional = bookingRepository.findById(bookingUpdateInput.getBookingId());
        if (!bookingOptional.isPresent()) {
            messageBucket.addMessage("Booking is not found!", ResponseMessageSeverity.ERROR);
            return;
        }

        final Booking booking = bookingOptional.get();

        final Long propertyId = booking.getPropertyId();
        final LocalDate startDate = bookingUpdateInput.getStartDate();
        final LocalDate endDate = bookingUpdateInput.getEndDate();

        blockValidationService.validateDatesAreAvailable(propertyId, startDate, endDate);

        if (BookingStatus.CANCELED.equals(bookingUpdateInput.getBookingStatus()) && BookingStatus.CANCELED.equals(booking.getBookingStatus())) {
            messageBucket.addMessage("Booking is already canceled before!", ResponseMessageSeverity.ERROR);
            return;
        }

        final Optional<Booking> bookingOtherOptional = bookingRepository.findActiveBookingsByPropertyIdAndStartDateAndEndDate(
                propertyId, startDate, endDate);
        if (bookingOtherOptional.isPresent() && bookingOtherOptional.get().getId() != booking.getId()) {
            messageBucket.addMessage("Dates are not available for this property! There is a booking made by someone else", ResponseMessageSeverity.ERROR);
        }
    }
}
