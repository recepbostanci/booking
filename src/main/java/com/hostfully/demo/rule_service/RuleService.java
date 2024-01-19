package com.hostfully.demo.rule_service;

/**
 * @author recepb
 */
public interface RuleService<T> {

    void apply(T t);
}
