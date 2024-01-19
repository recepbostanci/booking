package com.hostfully.demo.input_output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author recepb
 */

@Getter
@Setter
@RequiredArgsConstructor
public class BlockCreateInput extends BookingBaseInput {

    @NotNull
    private Long propertyId;

}
