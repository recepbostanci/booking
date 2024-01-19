package com.hostfully.demo.rule_service;

import com.hostfully.demo.booking.Booking;
import com.hostfully.demo.booking.BookingRepository;
import com.hostfully.demo.input_output.BookingCreateInput;
import com.hostfully.demo.rest.MessageBucket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingCreateRuleServiceTest {

    @InjectMocks
    private BookingCreateRuleService bookingCreateRuleService;

    @Mock
    private MessageBucket messageBucket;

    @Mock
    private BlockValidationService blockValidationService;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    void should_check_dates_are_blocked() {

        final BookingCreateInput bookingCreateInput = new BookingCreateInput();

        bookingCreateRuleService.apply(bookingCreateInput);

        verify(blockValidationService, times(1)).validateDatesAreAvailable(any(), any(), any());
    }

    @Test
    void error_when_conflict_with_current_bookings() {

        final BookingCreateInput bookingCreateInput = new BookingCreateInput();

        when(bookingRepository.findActiveBookingsByPropertyIdAndStartDateAndEndDate(any(), any(), any())).thenReturn(Optional.of(new Booking()));

        bookingCreateRuleService.apply(bookingCreateInput);

        verify(messageBucket, atLeastOnce()).addMessage(any(), any());
    }
}