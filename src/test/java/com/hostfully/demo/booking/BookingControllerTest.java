package com.hostfully.demo.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hostfully.demo.input_output.BlockCreateInput;
import com.hostfully.demo.input_output.BookingCreateInput;
import com.hostfully.demo.input_output.BookingUpdateInput;
import com.hostfully.demo.input_output.GuestInfoInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    private static final String BOOKING_BASE_URL = "/api/booking/";

    private static final String BOOKING_GET_ENDPOINT = "/api/booking/{propertyId}/{guestId}";

    private static final String BOOKING_DELETE_ENDPOINT = "/api/booking/{bookingId}";

    private static final String BLOCK_BASE_URL = "/api/booking/block/";

    private static final String BLOCK_DELETE_ENDPOINT = "/api/booking/block/{blockId}";

    private static final String BLOCK_GET_ENDPOINT = "/api/booking/block/{propertyId}";

    @Mock
    private BookingService bookingService;

    @Mock
    private BlockService blockService;

    @InjectMocks
    private BookingBaseInputValidator bookingBaseInputValidator;

    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).setDateFormat(simpleDateFormat);

        BookingController bookingController = new BookingController(bookingService, blockService, bookingBaseInputValidator);
        mvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void create_booking_using_post() throws Exception {

        final BookingCreateInput bookingCreateInput = new BookingCreateInput();
        bookingCreateInput.setGuestInfo(new GuestInfoInput());
        bookingCreateInput.setPropertyId(1l);
        bookingCreateInput.setStartDate(LocalDate.of(2022, 1, 1));
        bookingCreateInput.setEndDate(LocalDate.of(2022, 1, 5));
        mvc.perform(post(BOOKING_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingCreateInput)))
                .andExpect(status().isOk());
    }

    @Test
    void read_booking_using_get() throws Exception {

        mvc.perform(get(BOOKING_GET_ENDPOINT, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void update_booking_using_get() throws Exception {
        final BookingUpdateInput bookingUpdateInput = new BookingUpdateInput();
        bookingUpdateInput.setBookingId(1l);
        bookingUpdateInput.setBookingStatus(BookingStatus.CANCELED);
        bookingUpdateInput.setStartDate(LocalDate.of(2022, 1, 1));
        bookingUpdateInput.setEndDate(LocalDate.of(2022, 1, 5));
        mvc.perform(put(BOOKING_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingUpdateInput)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_booking() throws Exception {

        mvc.perform(delete(BOOKING_DELETE_ENDPOINT, 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void create_block_using_post() throws Exception {
        final BlockCreateInput blockCreateInput = new BlockCreateInput();
        blockCreateInput.setPropertyId(1l);
        blockCreateInput.setStartDate(LocalDate.of(2022, 1, 1));
        blockCreateInput.setEndDate(LocalDate.of(2022, 1, 5));
        mvc.perform(post(BLOCK_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blockCreateInput)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_block() throws Exception {

        mvc.perform(delete(BLOCK_DELETE_ENDPOINT, 1l)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void read_blocks_using_get() throws Exception {

        mvc.perform(get(BLOCK_GET_ENDPOINT, 1l)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}