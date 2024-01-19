package com.hostfully.demo.booking;

import com.hostfully.demo.input_output.BlockCreateInput;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rule_service.RuleProcessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlockServiceTest {

    @InjectMocks
    private BlockService blockService;

    @Mock
    private MessageBucket messageBucket;

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private RuleProcessService ruleProcessService;

    @Test
    void create_should_call_rule_process() {

        final BlockCreateInput blockCreateInput = getBlockCreateInput();

        blockService.create(blockCreateInput);

        verify(ruleProcessService, times(1)).apply(any());
    }

    @Test
    void create_should_return_with_block_id() {

        final BlockCreateInput blockCreateInput = getBlockCreateInput();

        final Block block = new Block();
        block.setId(1l);
        when(blockRepository.save(any())).thenReturn(block);

        final Block responseBlock = blockService.create(blockCreateInput);

        assertEquals(responseBlock.getId(), block.getId());
    }

    private BlockCreateInput getBlockCreateInput() {
        final BlockCreateInput blockCreateInput = new BlockCreateInput();
        blockCreateInput.setPropertyId(1l);
        blockCreateInput.setStartDate(LocalDate.of(2022, 1, 1));
        blockCreateInput.setEndDate(LocalDate.of(2022, 1, 5));
        return blockCreateInput;
    }

    @Test
    void delete_when_record_exist() {
        final long blockId = 1l;
        final Block block = new Block();
        block.setId(blockId);

        when(blockRepository.findById(eq(blockId))).thenReturn(Optional.of(block));

        blockService.delete(blockId);

        verify(blockRepository, times(1)).deleteById(blockId);
    }

    @Test
    void do_not_delete_when_record_not_exist() {
        final long blockId = 1l;
        final Block block = new Block();
        block.setId(blockId);

        when(blockRepository.findById(eq(blockId))).thenReturn(Optional.empty());

        blockService.delete(blockId);

        verify(blockRepository, times(0)).deleteById(blockId);
    }

    @Test
    void read_block_db_equal() {

        final long propertyId = 1l;
        List<Block> blocksDb = Arrays.asList(new Block());

        when(blockRepository.findByPropertyId(eq(propertyId))).thenReturn(blocksDb);

        final List<Block> blocksResponse = blockService.read(propertyId);

        assertEquals(blocksDb, blocksResponse);
    }

}