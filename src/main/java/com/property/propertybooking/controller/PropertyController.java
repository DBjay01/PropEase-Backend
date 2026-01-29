package com.property.propertybooking.controller;

import java.util.List;

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
import com.property.propertybooking.dto.PropertyViewRequest;
import com.property.propertybooking.dto.PropertyViewsCountDto;
import com.property.propertybooking.dto.PropertyWithImageDto;
import com.property.propertybooking.entity.Property;
import com.property.propertybooking.entity.PropertyViews;
import com.property.propertybooking.service.PropertyImageService;
import com.property.propertybooking.service.PropertyService;
import com.property.propertybooking.service.ViewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final PropertyImageService imageService;
    private final ViewService viewService;

//    public PropertyController(PropertyService propertyService , PropertyImageService imageService) {
//        this.propertyService = propertyService;
//        this.imageService = imageService;
//    }

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
    
    @GetMapping
    public ResponseEntity<List<PropertyResponse>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
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
    
    @PostMapping("/{id}/images")
    public ResponseEntity<List<String>> uploadImages(
            @PathVariable Long id,
            @RequestParam("images") MultipartFile[] files) {

        List<String> urls = imageService.uploadAndSaveImages(id, files);
        return ResponseEntity.ok(urls);
    }
    
    
    
    
 // Seller personal properties
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<PropertyWithImageDto>> getSellerProperties(
            @PathVariable Long sellerId) {

        return ResponseEntity.ok(
                propertyService.getPropertiesBySeller(sellerId)
        );
    }

    @PostMapping("/view")
    public ResponseEntity<String> addView(
            @RequestBody PropertyViewRequest request) {

        PropertyViews saved = viewService.addView(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("view saved: " + saved.getId());
    }

    @GetMapping("/{propertyId}/views")
        public ResponseEntity<List<PropertyViews>> getViewsForProperty(@PathVariable Long propertyId) {
            List<PropertyViews> views = viewService.getViewsByPropertyId(propertyId);
            return ResponseEntity.ok(views);
        }
    
    @GetMapping("/sellers/{sellerId}/viewsCount")
        public ResponseEntity<List<PropertyViewsCountDto>> getViewsCountForSeller(@PathVariable Long sellerId) {
            List<PropertyViewsCountDto> counts = viewService.getViewsCountForSeller(sellerId);
            return ResponseEntity.ok(counts);
        }
//    @PostMapping("/{propertyId}/images")
//    public ResponseEntity<String> uploadImages(
//            @PathVariable Long propertyId,
//            @RequestParam("files") MultipartFile[] files
//    ) {
//        propertyService.uploadPropertyImages(propertyId, files);
//        return ResponseEntity.ok("Images uploaded successfully");
//    }
//    
//    @PostMapping("/{id}/image")
//    public ResponseEntity<List<String>> uploadImages(
//            @PathVariable Long id,
//            @RequestParam("images") List<MultipartFile> files) {
// 
//        List<String> urls = files.stream()
//                .map(file -> cloudinaryService.uploadImage(file, id))
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(urls);
//    }
    
    

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
