package com.property.propertybooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.property.propertybooking.dto.PropertyNoImageDto;
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


    
 // Custom query returning DTO without images (uses seller.userId)
    @Query("SELECT new com.property.propertybooking.dto.PropertyNoImageDto(" +
           "p.propertyId, p.title, p.description, p.price, p.city, p.state, p.pincode, " +
           "p.propertyType, p.status, p.seller.name, p.createdAt) " +
           "FROM Property p")
    List<PropertyNoImageDto> findAllWithoutImages();
}
