package com.property.propertybooking.service;

import com.property.propertybooking.dto.PropertyRequest;
import com.property.propertybooking.dto.PropertyResponse;
import com.property.propertybooking.entity.Property;
import com.property.propertybooking.entity.User;

public interface PropertyService {
	
	Property createProperty(PropertyRequest request, User seller);
	PropertyResponse getPropertyById(Long propertyId);
	Property updateProperty(Long id, Property updatedData, User currentUser);
	void deleteProperty(Long id, User currentUser);
}
