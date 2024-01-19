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
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "block" )
public class Block {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long propertyId;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createDate;

    private String createUser;

}
