package com.hostfully.demo.input_output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author recepb
 */

@Getter
@Setter
@RequiredArgsConstructor
public class BookingDeleteInput {

    private Long blockId;

    private LocalDate startDate;

    private LocalDate endDate;

}
