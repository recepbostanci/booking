package com.hostfully.demo.booking;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * @author recepb
 */
@Converter(autoApply = true)
class BookingStatusAttributeConverter implements AttributeConverter<BookingStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookingStatus bookingStatus) {

        if (Objects.isNull(bookingStatus)) {
            return null;
        }
        return bookingStatus.getValue();
    }

    @Override
    public BookingStatus convertToEntityAttribute(Integer value) {
        return Objects.isNull(value) ? null : BookingStatus.convertToEnum(value);
    }
}
