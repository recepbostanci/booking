package com.hostfully.demo.booking;

import com.hostfully.demo.input_output.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author recepb
 */

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "id", target = "bookingId")
    BookingOutput convertToOutput(Booking booking);

    Booking convertFromInput(BookingCreateInput booking);

    List<BookingOutput> convertToOutputs(List<Booking> bookings);

    Block convertFromInput(BlockCreateInput blockCreateInput);

    @Mapping(source = "id", target = "blockId")
    BlockOutput convertToOutput(Block block);

    List<BlockOutput> convertToBlockOutputs(List<Block> blocks);

    Guest convertFromInput(GuestInfoInput guestInfoInput);

    @Mapping(target = "id", ignore = true)
    void merge(GuestInfoInput guestInfoInput, @MappingTarget Guest guest);

}
