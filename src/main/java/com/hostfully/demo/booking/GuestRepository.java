package com.hostfully.demo.booking;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author recepb
 */

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long> {

}
