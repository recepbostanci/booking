package com.hostfully.demo.booking;

import com.hostfully.demo.input_output.*;
import com.hostfully.demo.rest.RestResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author recepb
 */

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
class BookingController {

    private final BookingService bookingService;

    private final BlockService blockService;

    private final BookingBaseInputValidator bookingBaseInputValidator;

    @InitBinder({"bookingCreateInput", "bookingUpdateInput", "blockCreateInput"})
    protected void initBookingCreationInputBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(bookingBaseInputValidator);
    }

    // @formatter:off
    @BookingApiOperation(summary = "Creates a booking with given parameters",
                        description = "Creates a booking with given property, guest and date infos. Booking state is " +
                                "marked as Active",
                        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "",
                        content = { @Content (
                                    mediaType = "application/json",
                                    schema = @Schema (implementation = BookingCreateInput.class),
                                    examples = @ExampleObject(
                                            name = "Creates a booking",
                                            summary = "Creates a booking",
                                            description = "Creates a booking with given parameters",
                                            value = "{\n \"guestInfo\": {\n \"name\": \"Recep\", \n \"surname\": \"Bostanci\"," +
                                            " \"birthDate\": \"1989-02-14\" \n},\n \"propertyId\": 1,\n \"startDate\": " +
                                            "\"2022-03-01\",\n \"endDate\": \"2022-03-05\"\n }"
                                    )
                        )

    }))
    // @formatter:on
    @PostMapping
    ResponseEntity<RestResponse<BookingOutput>> create(@Validated @RequestBody BookingCreateInput bookingCreateInput) {
        final Booking booking = bookingService.create(bookingCreateInput);
        return ResponseEntity.ok(RestResponse.of(BookingMapper.INSTANCE.convertToOutput(booking)));
    }

    // @formatter:off
    @BookingApiOperation(summary = "Fetches booking list with given parameters",
            description = "Fetches booking list by property and guest ids")
    // @formatter:on
    @GetMapping("/{propertyId}/{guestId}")
    ResponseEntity<RestResponse<List<BookingOutput>>> read(@PathVariable @Parameter(required = true, description = "property id", example = "1") Long propertyId, @PathVariable @Parameter(required = true, description = "guest id", example = "1") Long guestId) {
        final BookingReadInput bookingReadInput = BookingReadInput.builder().propertyId(propertyId).guestId(guestId).build();
        final List<Booking> bookings = bookingService.read(bookingReadInput);
        return ResponseEntity.ok(RestResponse.of(BookingMapper.INSTANCE.convertToOutputs(bookings)));
    }

    // @formatter:off
    @BookingApiOperation(summary = "Updates a booking with given parameters",
            description = "Updates a booking with given property, guest and date infos. If booking state is " +
                    "sent as Canceled, dates are ignored and state marked as Canceled. BookingStatus value can be 'ACTIVE' or 'CANCELED'"
            ,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema (implementation = BookingUpdateInput.class),
                            examples = @ExampleObject(
                                    name = "Updates a booking",
                                    summary = "Updates a booking",
                                    description = "Updates a booking with given parameters",
                                    value = "{\n \"bookingId\": 1,\n \"bookingStatus\": \"ACTIVE\",\n \"startDate\": " +
                                            "\"2022-03-01\",\n \"endDate\": \"2022-03-05\", \n \"guestInfo\": {\n \"name\": \"Recep\", " +
                                            "\n \"surname\": \"Bostanci\", \n \"birthDate\": \"1989-02-14\" \n} \n}"
                            )
                            )

                    })
    )
    // @formatter:on
    @PutMapping
    ResponseEntity<RestResponse<BookingOutput>> update(@RequestBody BookingUpdateInput bookingUpdateInput) {

        final Booking booking = bookingService.update(bookingUpdateInput);
        return ResponseEntity.ok(RestResponse.of(BookingMapper.INSTANCE.convertToOutput(booking)));
    }

    // @formatter:off
    @BookingApiOperation(summary = "Deletes a booking with by booking id",
            description = "Deletes a booking with by booking id")
    // @formatter:on
    @DeleteMapping("/{bookingId}")
    ResponseEntity<RestResponse<Void>> delete(@PathVariable @Parameter(required = true, description = "booking id", example = "1") Long bookingId) {
        bookingService.delete(bookingId);
        return ResponseEntity.ok(RestResponse.empty());
    }

    // @formatter:off
    @BlockApiOperation(summary = "Creates a block with given parameters",
            description = "Creates a block with given property and date infos. " +
                    "After definition, no booking can be created within these dates until deleting the block",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema (implementation = BlockCreateInput.class),
                            examples = @ExampleObject(
                                    name = "Creates a block",
                                    summary = "Creates a block",
                                    description = "Creates a block with given parameters",
                                    value = "{\"propertyId\": 1,\n \"startDate\": " +
                                            "\"2022-03-01\",\n \"endDate\": \"2022-03-05\"\n }"
                            )
                    )

                    }))
    // @formatter:on
    @PostMapping("/block")
    ResponseEntity<RestResponse<BlockOutput>> create(@Validated @RequestBody BlockCreateInput blockCreateInput) {
        final Block block = blockService.create(blockCreateInput);
        return ResponseEntity.ok(RestResponse.of(BookingMapper.INSTANCE.convertToOutput(block)));
    }

    // @formatter:off
    @BlockApiOperation(summary = "Deletes a block by block id",
            description = "Deletes a block by block id")
    // @formatter:on
    @DeleteMapping("/block/{blockId}")
    ResponseEntity<RestResponse<Void>> deleteBlock(@PathVariable @NotNull @Parameter(required = true, description = "booking id", example = "1") Long blockId) {
        blockService.delete(blockId);
        return ResponseEntity.ok(RestResponse.empty());
    }

    // @formatter:off
    @BlockApiOperation(summary = "Fetches block list with given parameter",
            description = "Fetches block list by property id")
    // @formatter:on
    @GetMapping("/block/{propertyId}")
    ResponseEntity<RestResponse<List<BlockOutput>>> readBlocks(@PathVariable @Parameter(required = true, description = "property id", example = "1") Long propertyId) {
        final List<Block> blocks = blockService.read(propertyId);
        return ResponseEntity.ok(RestResponse.of(BookingMapper.INSTANCE.convertToBlockOutputs(blocks)));
    }

}
