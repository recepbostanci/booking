package com.hostfully.demo.booking;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author recepb
 */

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findByPropertyIdAndGuestId(Long propertyId, Long guestId);

    @Query("Select b FROM booking b WHERE b.propertyId=:propertyId " +
            "and b.startDate < :endDate " +
            "and b.endDate > :startDate " +
            "and b.bookingStatus=1")
    Optional<Booking> findActiveBookingsByPropertyIdAndStartDateAndEndDate(Long propertyId, LocalDate startDate, LocalDate endDate);

}
