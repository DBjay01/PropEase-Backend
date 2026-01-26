package com.property.propertybooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.property.propertybooking.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>{
	
}
