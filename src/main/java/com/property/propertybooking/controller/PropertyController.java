package com.property.propertybooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.property.propertybooking.dto.PropertyRequest;
import com.property.propertybooking.dto.PropertyResponse;
import com.property.propertybooking.entity.Property;
import com.property.propertybooking.service.PropertyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "http://localhost:3000")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // CREATE PROPERTY
    @PostMapping
    public ResponseEntity<Property> createProperty(
            @Valid @RequestBody PropertyRequest request
    ) {
        Property savedProperty = propertyService.createProperty(request);
        return new ResponseEntity<>(savedProperty, HttpStatus.CREATED);
    }

    // GET PROPERTY BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    // UPDATE PROPERTY
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(
            @PathVariable Long id,
            @RequestBody Property property
    ) {
        Property updatedProperty = propertyService.updateProperty(id, property);
        return ResponseEntity.ok(updatedProperty);
    }

    // DELETE PROPERTY
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build(); // 204
    }
    
    @PostMapping("/{propertyId}/images")
    public ResponseEntity<String> uploadImages(
            @PathVariable Long propertyId,
            @RequestParam("files") MultipartFile[] files
    ) {
        propertyService.uploadPropertyImages(propertyId, files);
        return ResponseEntity.ok("Images uploaded successfully");
    }
}


//package com.property.propertybooking.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.property.propertybooking.dto.PropertyRequest;
//import com.property.propertybooking.dto.PropertyResponse;
//import com.property.propertybooking.entity.Property;
//import com.property.propertybooking.entity.User;
//import com.property.propertybooking.service.PropertyService;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/api/properties")
//@CrossOrigin(origins = "http://localhost:3000")
//public class PropertyController {
//	
//	 private final PropertyService propertyService;
//
//	    public PropertyController(PropertyService propertyService) {
//	        this.propertyService = propertyService;
//	    }
//	    
//	    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
//	    @PostMapping
//	    public ResponseEntity<Property> createProperty(
//	            @Valid @RequestBody PropertyRequest request,
//	            @AuthenticationPrincipal User loggedInUser
//	    ) {
//	        Property savedProperty =
//	                propertyService.createProperty(request, loggedInUser);
//
//	        return new ResponseEntity<>(savedProperty, HttpStatus.CREATED);
//	    }
//	    
//	    /*
//	     * 
//	     * Security NOTE (IMPORTANT)
//			Your User entity must implement UserDetails for this to work:
//			@AuthenticationPrincipal User loggedInUser
//			And roles should be:
//				
//			ROLE_SELLER
//			ROLE_ADMIN*/
//	    
//	    @GetMapping("/{id}")
//	    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable Long id) {
//	        return ResponseEntity.ok(propertyService.getPropertyById(id));
//	    }
//
//	    @PutMapping("/{id}")
//	    public ResponseEntity<Property> updateProperty(
//	            @PathVariable Long id,
//	            @RequestBody Property property,
//	            @AuthenticationPrincipal User currentUser
//	    ) {
//	        Property updatedProperty =
//	                propertyService.updateProperty(id, property, currentUser);
//
//	        return ResponseEntity.ok(updatedProperty);
//	    }
//	    
//	    @DeleteMapping("/{id}")
//	    public ResponseEntity<Void> deleteProperty(
//	            @PathVariable Long id,
//	            @AuthenticationPrincipal User currentUser
//	    ) {
//	        propertyService.deleteProperty(id, currentUser);
//	        return ResponseEntity.noContent().build(); // 204
//	    }
//	   
//
//}
//
//
