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
public interface BlockRepository extends CrudRepository<Block, Long> {

    @Query("Select b FROM block b WHERE b.propertyId=:propertyId " +
            "and b.startDate < :endDate " +
            "and b.endDate > :startDate ")
    Optional<Block> findByPropertyIdAndStartDateAndEndDate(Long propertyId, LocalDate startDate, LocalDate endDate);

    List<Block> findByPropertyId(Long propertyId);
}
