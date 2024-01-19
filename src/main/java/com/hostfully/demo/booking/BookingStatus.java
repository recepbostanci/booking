package com.hostfully.demo.booking;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Objects;

/**
 * @author recepb
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum BookingStatus {

    ACTIVE(1),
    CANCELED(2);

//    @JsonValue
    private Integer value;

    BookingStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static BookingStatus convertToEnum(Integer value) {
        if (Objects.nonNull(value)) {
            for (BookingStatus bookingStatus : BookingStatus.values()) {
                if (bookingStatus.getValue() == value) {
                    return bookingStatus;
                }
            }
        }
        return null;
    }
}
