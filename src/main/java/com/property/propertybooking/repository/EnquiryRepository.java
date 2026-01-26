package com.property.propertybooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.property.propertybooking.entity.Enquiry;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
}
