package com.hostfully.demo.rule_service;

import com.hostfully.demo.booking.Block;
import com.hostfully.demo.booking.BlockRepository;
import com.hostfully.demo.booking.Booking;
import com.hostfully.demo.booking.BookingRepository;
import com.hostfully.demo.input_output.BlockCreateInput;
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
class BlockCreateRuleServiceTest {

    @InjectMocks
    private BlockCreateRuleService blockCreateRuleService;

    @Mock
    private MessageBucket messageBucket;

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    void error_when_conflict_with_other_blocks() {

        final BlockCreateInput blockCreateInput = new BlockCreateInput();

        when(blockRepository.findByPropertyIdAndStartDateAndEndDate(any(), any(), any())).thenReturn(Optional.of(new Block()));

        blockCreateRuleService.apply(blockCreateInput);

        verify(messageBucket, atLeastOnce()).addMessage(any(), any());
    }

    @Test
    void error_when_conflict_with_current_bookings() {

        final BlockCreateInput blockCreateInput = new BlockCreateInput();

        when(bookingRepository.findActiveBookingsByPropertyIdAndStartDateAndEndDate(any(), any(), any())).thenReturn(Optional.of(new Booking()));

        blockCreateRuleService.apply(blockCreateInput);

        verify(messageBucket, atLeastOnce()).addMessage(any(), any());
    }
}