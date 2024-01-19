package com.hostfully.demo.booking;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author recepb
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "booking" )
public class Booking {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long propertyId;

    private Long guestId;

    private BookingStatus bookingStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private String createUser;

}
