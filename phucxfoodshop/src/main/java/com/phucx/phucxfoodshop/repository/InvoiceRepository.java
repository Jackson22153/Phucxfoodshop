package com.phucx.phucxfoodshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.phucx.phucxfoodshop.model.Invoice;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    @Query("""
        SELECT i FROM Invoice i \
        WHERE orderID=?1 AND customerID=?2\
            """)
    List<Invoice> findByOrderIDAndCustomerID(String orderID, String customerID);
}
