package com.hostfully.demo.booking;

import com.hostfully.demo.input_output.BookingCreateInput;
import com.hostfully.demo.input_output.BookingReadInput;
import com.hostfully.demo.input_output.BookingUpdateInput;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rest.ResponseMessageSeverity;
import com.hostfully.demo.rule_service.RuleProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.hostfully.demo.booking.BookingStatus.ACTIVE;
import static com.hostfully.demo.booking.BookingStatus.CANCELED;

/**
 * <p>
 * manage bookings - create, read, update, delete
 * </p>
 *
 * @author recepb
 */

@Service
@RequiredArgsConstructor
class BookingService {

    private final MessageBucket messageBucket;

    private final GuestRepository guestRepository;

    private final BookingRepository bookingRepository;

    private final RuleProcessService ruleProcessService;

    public Booking create(BookingCreateInput bookingCreateInput) {

        ruleProcessService.apply(bookingCreateInput);

        final Booking booking = BookingMapper.INSTANCE.convertFromInput(bookingCreateInput);

        booking.setBookingStatus(ACTIVE);
        booking.setCreateDate(LocalDateTime.now());
        booking.setLastUpdateDate(LocalDateTime.now());
        booking.setCreateUser("username"); // get username security context

        final Guest guest = BookingMapper.INSTANCE.convertFromInput(bookingCreateInput.getGuestInfo());
        final Guest savedGuest = guestRepository.save(guest);
        booking.setGuestId(savedGuest.getId());

        final Booking savedBooking = bookingRepository.save(booking);

        messageBucket.addMessage("Booking has been created successfully", ResponseMessageSeverity.INFO);
        return savedBooking;
    }

    public List<Booking> read(BookingReadInput bookingReadInput) {
        return bookingRepository.findByPropertyIdAndGuestId(bookingReadInput.getPropertyId(), bookingReadInput.getGuestId());
    }

    public Booking update(BookingUpdateInput bookingUpdateInput) {

        ruleProcessService.apply(bookingUpdateInput);

        final Optional<Booking> bookingOptional = bookingRepository.findById(bookingUpdateInput.getBookingId());
        final Booking booking = bookingOptional.get(); // existency already validated in rule service.

        if (CANCELED.equals(bookingUpdateInput.getBookingStatus()) && ACTIVE.equals(booking.getBookingStatus())) {
            booking.setBookingStatus(bookingUpdateInput.getBookingStatus());
            final Booking canceledBooking = bookingRepository.save(booking);
            messageBucket.addMessage("Booking canceled successfully", ResponseMessageSeverity.INFO);
            return canceledBooking;
        }

        booking.setBookingStatus(bookingUpdateInput.getBookingStatus());
        booking.setStartDate(bookingUpdateInput.getStartDate());
        booking.setEndDate(bookingUpdateInput.getEndDate());

        final Optional<Guest> guestOptional = guestRepository.findById(booking.getId());
        guestOptional.ifPresent(guest -> {
            BookingMapper.INSTANCE.merge(bookingUpdateInput.getGuestDetail(), guest);
            guestRepository.save(guest);
            messageBucket.addMessage("Guest infos have been updated successfully", ResponseMessageSeverity.INFO);
        });

        final Booking updatedBooking = bookingRepository.save(booking);
        messageBucket.addMessage("Booking has been updated successfully", ResponseMessageSeverity.INFO);
        return updatedBooking;
    }


    public void delete(Long bookingId) {

        final Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (!bookingOptional.isPresent()) {
            messageBucket.addMessage("Booking is not found!", ResponseMessageSeverity.ERROR);
            return;
        }

        bookingRepository.deleteById(bookingId);
        messageBucket.addMessage("Booking has been deleted successfully", ResponseMessageSeverity.INFO);
    }
}