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
public class BookingCreateInput extends BookingBaseInput {

    @NotNull
    private GuestInfoInput guestInfo;

    @NotNull
    private Long propertyId;

}
