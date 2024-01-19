package com.hostfully.demo.rule_service;

import com.hostfully.demo.booking.Block;
import com.hostfully.demo.booking.BlockRepository;
import com.hostfully.demo.rest.MessageBucket;
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
class BlockValidationServiceTest {

    @InjectMocks
    private BlockValidationService blockValidationService;

    @Mock
    private MessageBucket messageBucket;

    @Mock
    private BlockRepository blockRepository;

    @Test
    void validate_dates_are_available() {
        final long propertyId = 1l;
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 5);

        when(blockRepository.findByPropertyIdAndStartDateAndEndDate(propertyId, startDate, endDate)).thenReturn(Optional.of(new Block()));

        blockValidationService.validateDatesAreAvailable(propertyId, startDate, endDate);

        verify(messageBucket, times(1)).addMessage(any(), any());
    }
}