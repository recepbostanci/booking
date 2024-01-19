package com.hostfully.demo.input_output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author recepb
 */

@Getter
@Setter
@Builder
public class BookingReadInput {

    @NotNull
    private Long guestId;

    @NotNull
    private Long propertyId;

}
