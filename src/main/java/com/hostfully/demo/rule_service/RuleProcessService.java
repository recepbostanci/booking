package com.hostfully.demo.rule_service;

import com.hostfully.demo.app.BusinessException;
import com.hostfully.demo.rest.MessageBucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Calls certain rule service according to input type.
 * If an error occured and put in messagebucket, throws business exception.
 * </p>
 * @author recepb
 */

//  TODO We can implement this locator with any other ways, using qualifiers, custom annotations etc.
//   In addition, to increase efficiency, in every get process we can put input-ruleservice pairs to a hashmap that is used as cache.
@Slf4j
@Service
@RequiredArgsConstructor
public class RuleProcessService {

    private final List<RuleService> ruleServices;

    private final MessageBucket messageBucket;

    public void apply(Object input) {

        final Optional<RuleService> ruleServiceOptional =
                ruleServices.stream().filter(ruleService -> input.getClass()
                        .isAssignableFrom(GenericTypeResolver.resolveTypeArgument(ruleService.getClass(), RuleService.class))).findFirst();

        if (ruleServiceOptional.isPresent()) {
            ruleServiceOptional.get().apply(input);

        } else {
            log.warn("Can't handle rule service for " + input);
        }

        if (messageBucket.hasErrors()) {
            throw new BusinessException();
        }
    }
}
