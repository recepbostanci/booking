package com.hostfully.demo.input_output;

import com.hostfully.demo.booking.BookingStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author recepb
 */

@Getter
@Setter
@RequiredArgsConstructor
public class BookingOutput {

    private Long bookingId;

    private Long guestId;

    private Long propertyId;

    private BookingStatus bookingStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastUpdateDate;

}
