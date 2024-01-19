package com.hostfully.demo.rule_service;

import com.hostfully.demo.input_output.BlockCreateInput;
import com.hostfully.demo.rest.MessageBucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RuleProcessServiceTest {

    @InjectMocks
    private RuleProcessService ruleProcessService;

    @Mock
    private List<RuleService> ruleServices = new ArrayList<>();

    @Mock
    private MessageBucket messageBucket;

    @Test
    void throw_exception_when_has_errors() {

        when(messageBucket.hasErrors()).thenReturn(false);

        Assertions.assertThrows(Exception.class, () -> {
            ruleProcessService.apply(eq(new BlockCreateInput()));
        });
    }
}