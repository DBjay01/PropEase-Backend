package com.property.propertybooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.property.propertybooking.entity.Property;
import com.property.propertybooking.entity.PropertyStatus;

public interface PropertyRepository extends JpaRepository<Property, Long>{
	
// Count properties by status (APPROVED / PENDING / REJECTED)
    // Used in Admin Dashboard stats
    long countByStatus(PropertyStatus status);

    // Search properties by title (Admin can search all properties)
    List<Property> findByTitleContainingIgnoreCase(String title);

  
 // ✅ Seller personal properties (CORRECT)
    List<Property> findBySeller_UserId(Long sellerId);


    
}
