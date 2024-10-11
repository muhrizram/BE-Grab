package com.muhrizram.grabprojectbe.repositories.oltps;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.muhrizram.grabprojectbe.models.oltps.OltpTransaction;

public interface OltpTransactionRepository extends MongoRepository<OltpTransaction, String> {
    Optional<OltpTransaction> findById(String id);

    Page<OltpTransaction> findByPaxId(String paxId, Pageable pageable);

    @Query("{ 'pax.id': ?0, '$or': [ " +
            "{ 'menu.name': { $regex: ?1, $options: 'i' } }, " +
            "] }")
    Page<OltpTransaction> findByPaxIdAndMultiFieldSearch(String paxId, String search, Pageable pageable);

}
