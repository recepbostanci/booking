package com.hostfully.demo.rule_service;

import com.hostfully.demo.booking.Block;
import com.hostfully.demo.booking.BlockRepository;
import com.hostfully.demo.booking.Booking;
import com.hostfully.demo.booking.BookingRepository;
import com.hostfully.demo.input_output.BlockCreateInput;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rest.ResponseMessageSeverity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author recepb
 */
@Service
@RequiredArgsConstructor
class BlockCreateRuleService implements RuleService<BlockCreateInput> {

    private final MessageBucket messageBucket;

    private final BlockRepository blockRepository;

    private final BookingRepository bookingRepository;

    @Override
    public void apply(BlockCreateInput blockCreateInput) {

        final Optional<Block> blockOptional = blockRepository.findByPropertyIdAndStartDateAndEndDate(
                blockCreateInput.getPropertyId(), blockCreateInput.getStartDate(), blockCreateInput.getEndDate());
        if (blockOptional.isPresent()) {
            messageBucket.addMessage("Block dates conflicted with a current block for this property!", ResponseMessageSeverity.ERROR);
        }

        checkExistBookings(blockCreateInput);
    }

    private void checkExistBookings(BlockCreateInput blockCreateInput) {
        final Optional<Booking> bookingOptional = bookingRepository.findActiveBookingsByPropertyIdAndStartDateAndEndDate(
                blockCreateInput.getPropertyId(), blockCreateInput.getStartDate(), blockCreateInput.getEndDate());

        if (!bookingOptional.isPresent()) {
            return;
        }
        messageBucket.addMessage("Block dates conflicted with a current booking for this property!", ResponseMessageSeverity.ERROR);
    }
}
