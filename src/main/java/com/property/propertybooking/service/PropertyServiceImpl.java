package com.property.propertybooking.service;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.property.propertybooking.dto.PropertyRequest;
import com.property.propertybooking.dto.PropertyResponse;
import com.property.propertybooking.entity.Property;
import com.property.propertybooking.entity.PropertyStatus;
import com.property.propertybooking.entity.Role;
import com.property.propertybooking.entity.User;
import com.property.propertybooking.repository.PropertyRepository;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Property createProperty(PropertyRequest request, User seller) {

        Property property = new Property();
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setPrice(request.getPrice());
        property.setCity(request.getCity());
        property.setState(request.getState());
        property.setPincode(request.getPincode());
        property.setPropertyType(request.getPropertyType());

        // default status if not provided
        property.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : PropertyStatus.AVAILABLE
        );

        property.setSeller(seller);

        return propertyRepository.save(property);
    }

	@Override
	public PropertyResponse getPropertyById(Long propertyId) {
		Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
		
		PropertyResponse response = new PropertyResponse();
        response.setPropertyId(property.getPropertyId());
        response.setTitle(property.getTitle());
        response.setDescription(property.getDescription());
        response.setPrice(property.getPrice());
        response.setCity(property.getCity());
        response.setState(property.getState());
        response.setPincode(property.getPincode());
        response.setPropertyType(property.getPropertyType());
        response.setStatus(property.getStatus());
        response.setCreatedAt(property.getCreatedAt());
        
        response.setSellerId(property.getSeller().getUserId());
        response.setSellerName(property.getSeller().getName());
        
        response.setImages(Collections.emptyList());
        
        return response;
		
	}

	@Override
	public Property updateProperty(Long id, Property updatedData, User currentUser) {
		
		Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
		
		boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);
        boolean isOwner = existingProperty.getSeller().getUserId()
                .equals(currentUser.getUserId());
        
        if (!isAdmin && !isOwner) {
            throw new RuntimeException("You are not allowed to update this property");
        }
        
        if (updatedData.getTitle() != null)
            existingProperty.setTitle(updatedData.getTitle());

        if (updatedData.getDescription() != null)
            existingProperty.setDescription(updatedData.getDescription());

        if (updatedData.getPrice() != null)
            existingProperty.setPrice(updatedData.getPrice());

        if (updatedData.getCity() != null)
            existingProperty.setCity(updatedData.getCity());
        
        if (updatedData.getState() != null)
            existingProperty.setState(updatedData.getState());

        if (updatedData.getPincode() != null)
            existingProperty.setPincode(updatedData.getPincode());

        if (updatedData.getPropertyType() != null)
            existingProperty.setPropertyType(updatedData.getPropertyType());

        if (updatedData.getStatus() != null)
            existingProperty.setStatus(updatedData.getStatus());

        return propertyRepository.save(existingProperty);
		
	}

	@Override
	public void deleteProperty(Long id, User currentUser) {
		Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
		
		boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);
        boolean isOwner = property.getSeller().getUserId()
                .equals(currentUser.getUserId());
        
        if (!isAdmin && !isOwner) {
            throw new RuntimeException("You are not allowed to delete this property");
        }
        
        propertyRepository.delete(property);
	}
}
