package com.hostfully.demo.input_output;

import com.hostfully.demo.booking.BookingStatus;
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
public class BookingUpdateInput extends BookingBaseInput {

    @NotNull
    private Long bookingId;

    @NotNull
    private BookingStatus bookingStatus;

    @NotNull
    private GuestInfoInput guestDetail;

}
