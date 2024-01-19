package com.hostfully.demo.rule_service;

import com.hostfully.demo.booking.Block;
import com.hostfully.demo.booking.BlockRepository;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rest.ResponseMessageSeverity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class BlockValidationService {

    private final BlockRepository blockRepository;

    private final MessageBucket messageBucket;

    public void validateDatesAreAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) {

        final Optional<Block> blockOptional = blockRepository.findByPropertyIdAndStartDateAndEndDate(
                propertyId, startDate, endDate);
        if (blockOptional.isPresent()) {
            messageBucket.addMessage("Dates are not available for this property. There is a block within these dates!", ResponseMessageSeverity.ERROR);
        }
    }
}
