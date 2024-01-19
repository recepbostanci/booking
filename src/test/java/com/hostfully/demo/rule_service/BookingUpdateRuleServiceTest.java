package com.hostfully.demo.rule_service;

import com.hostfully.demo.booking.Booking;
import com.hostfully.demo.booking.BookingRepository;
import com.hostfully.demo.booking.BookingStatus;
import com.hostfully.demo.input_output.BookingUpdateInput;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rest.ResponseMessageSeverity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingUpdateRuleServiceTest {

    @InjectMocks
    private BookingUpdateRuleService bookingUpdateRuleService;

    @Mock
    private MessageBucket messageBucket;

    @Mock
    private BlockValidationService blockValidationService;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    void add_error_message_when_record_already_canceled_and_sent_as_canceled() {
        final BookingUpdateInput bookingUpdateInput = new BookingUpdateInput();
        bookingUpdateInput.setBookingId(1l);
        bookingUpdateInput.setBookingStatus(BookingStatus.CANCELED);
        bookingUpdateInput.setStartDate(LocalDate.of(2022, 1, 1));
        bookingUpdateInput.setEndDate(LocalDate.of(2022, 1, 5));

        Booking canceledBooking = new Booking();
        canceledBooking.setBookingStatus(BookingStatus.CANCELED);
        when(bookingRepository.findById(any())).thenReturn(Optional.of(canceledBooking));

        bookingUpdateRuleService.apply(bookingUpdateInput);

        verify(messageBucket, atLeastOnce()).addMessage(any(), eq(ResponseMessageSeverity.ERROR));
    }

    @Test
    void add_error_message_when_record_not_found() {

        when(bookingRepository.findById(any())).thenReturn(Optional.empty());

        bookingUpdateRuleService.apply(new BookingUpdateInput());

        verify(messageBucket, atLeastOnce()).addMessage(any(), eq(ResponseMessageSeverity.ERROR));
    }

    @Test
    void should_check_dates_are_blocked_if_record_exist() {

        when(bookingRepository.findById(any())).thenReturn(Optional.of(new Booking()));

        bookingUpdateRuleService.apply(new BookingUpdateInput());

        verify(blockValidationService, times(1)).validateDatesAreAvailable(any(), any(), any());
    }

    @Test
    void error_when_conflict_with_current_bookings_made_by_anyone() {

        final Booking bookingCurrent = new Booking();
        bookingCurrent.setId(1l);
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingCurrent));

        final Booking bookingAnyOne = new Booking();
        bookingAnyOne.setId(2l);
        when(bookingRepository.findActiveBookingsByPropertyIdAndStartDateAndEndDate(any(), any(), any())).thenReturn(Optional.of(bookingAnyOne));

        bookingUpdateRuleService.apply(new BookingUpdateInput());

        verify(messageBucket, atLeastOnce()).addMessage(any(), any());
    }
}