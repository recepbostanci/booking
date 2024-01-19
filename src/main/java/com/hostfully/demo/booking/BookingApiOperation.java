package com.hostfully.demo.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author recepb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation
public @interface BookingApiOperation {

    String summary();

    String description();

    RequestBody requestBody() default @RequestBody;

    String[] tags() default {"Booking Service Operation"};

}
