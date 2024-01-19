package com.hostfully.demo.input_output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author recepb
 */

@Getter
@Setter
@RequiredArgsConstructor
public class BookingBaseInput {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
