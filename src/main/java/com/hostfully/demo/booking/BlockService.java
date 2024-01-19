package com.hostfully.demo.booking;

import com.hostfully.demo.input_output.BlockCreateInput;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rest.ResponseMessageSeverity;
import com.hostfully.demo.rule_service.RuleProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 *  <p>
 *     manage blocks
 *  </p>
 *
 * @author recepb
 */
@Service
@RequiredArgsConstructor
class BlockService {

    private final BlockRepository blockRepository;

    private final MessageBucket messageBucket;

    private final RuleProcessService ruleProcessService;

    Block create(BlockCreateInput blockCreateInput) {

        ruleProcessService.apply(blockCreateInput);

        final Block block = BookingMapper.INSTANCE.convertFromInput(blockCreateInput);
        final Block savedBlock = blockRepository.save(block);
        messageBucket.addMessage("Block created successfully", ResponseMessageSeverity.INFO);

        return savedBlock;
    }

    void delete(Long blockId) {
        final Optional<Block> blockOptional = blockRepository.findById(blockId);
        if (blockOptional.isPresent()) {
            blockRepository.deleteById(blockId);
            messageBucket.addMessage("Block deleted successfully", ResponseMessageSeverity.INFO);
            return;
        }
        messageBucket.addMessage("Block not found!", ResponseMessageSeverity.ERROR);
    }

    List<Block> read(Long propertyId){
        return blockRepository.findByPropertyId(propertyId);
    }

}
