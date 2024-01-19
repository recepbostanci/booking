package com.hostfully.demo.booking;

import com.hostfully.demo.input_output.BookingCreateInput;
import com.hostfully.demo.input_output.BookingReadInput;
import com.hostfully.demo.input_output.BookingUpdateInput;
import com.hostfully.demo.input_output.GuestInfoInput;
import com.hostfully.demo.rest.MessageBucket;
import com.hostfully.demo.rule_service.RuleProcessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private MessageBucket messageBucket;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RuleProcessService ruleProcessService;

    @Test
    void create_should_call_rule_process() {

        when(guestRepository.save(any())).thenReturn(new Guest());

        final BookingCreateInput bookingCreateInput = getBookingCreateInput();

        bookingService.create(bookingCreateInput);

        verify(ruleProcessService, times(1)).apply(any());
    }

    @Test
    void create_should_return_with_booking_id() {

        final BookingCreateInput bookingCreateInput = getBookingCreateInput();

        final Booking booking = new Booking();
        booking.setId(1l);
        when(bookingRepository.save(any())).thenReturn(booking);
        when(guestRepository.save(any())).thenReturn(new Guest());

        final Booking responseBooking = bookingService.create(bookingCreateInput);

        assertEquals(responseBooking.getId(), booking.getId());
    }

    private BookingCreateInput getBookingCreateInput() {
        final BookingCreateInput bookingCreateInput = new BookingCreateInput();
        final GuestInfoInput guestInfo = new GuestInfoInput();
        guestInfo.setName("Recep");
        guestInfo.setSurname("Bostanci");
        guestInfo.setBirthDate(LocalDate.of(1989, 2, 14));
        bookingCreateInput.setGuestInfo(guestInfo);
        bookingCreateInput.setPropertyId(1l);
        bookingCreateInput.setStartDate(LocalDate.of(2022, 1, 1));
        bookingCreateInput.setEndDate(LocalDate.of(2022, 1, 5));
        return bookingCreateInput;
    }

    @Test
    void read_bookings_db_equal() {

        final long propertyId = 1l;
        final long guestId = 1l;
        final BookingReadInput bookingReadInput = BookingReadInput.builder().propertyId(propertyId).guestId(guestId).build();
        List<Booking> bookings = Arrays.asList(new Booking());

        when(bookingRepository.findByPropertyIdAndGuestId(eq(propertyId), eq(guestId))).thenReturn(bookings);

        final List<Booking> bookingsResponse = bookingService.read(bookingReadInput);

        assertEquals(bookings, bookingsResponse);
    }

    @Test
    void should_save_if_record_and_input_have_different_state() {
        final BookingUpdateInput bookingUpdateInput = new BookingUpdateInput();
        bookingUpdateInput.setBookingId(1l);
        bookingUpdateInput.setBookingStatus(BookingStatus.ACTIVE);
        bookingUpdateInput.setStartDate(LocalDate.of(2022, 1, 1));
        bookingUpdateInput.setEndDate(LocalDate.of(2022, 1, 5));

        final Booking canceledBooking = new Booking();
        canceledBooking.setBookingStatus(BookingStatus.CANCELED);
        when(bookingRepository.findById(any())).thenReturn(Optional.of(canceledBooking));

        final Booking responseBooking = bookingService.update(bookingUpdateInput);

        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void delete_when_record_exist() {
        final long bookingId = 1l;
        final Booking booking = new Booking();
        booking.setId(bookingId);

        when(bookingRepository.findById(eq(bookingId))).thenReturn(Optional.of(booking));

        bookingService.delete(bookingId);

        verify(bookingRepository, times(1)).deleteById(bookingId);
    }
}