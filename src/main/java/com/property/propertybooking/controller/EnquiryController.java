package com.property.propertybooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.property.propertybooking.dto.EnquiryCreateRequestDto;
import com.property.propertybooking.dto.EnquiryStatusUpdateDto;
import com.property.propertybooking.dto.EnquiryUpdateRequestDto;
import com.property.propertybooking.entity.Enquiry;
import com.property.propertybooking.service.EnquiryService;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    // API 1: Create Enquiry
    
    @PostMapping
    public ResponseEntity<Enquiry> createEnquiry(
            @RequestBody EnquiryCreateRequestDto dto) {

        // TEMPORARY buyerId 
        Long buyerId = 1L;

        Enquiry enquiry = enquiryService.createEnquiry(buyerId, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(enquiry);
    }
    
      // API 2: Get All Enquiries
       @GetMapping
       public ResponseEntity<List<Enquiry>> getAllEnquiries() {

        List<Enquiry> enquiries = enquiryService.getAllEnquiries();

        return ResponseEntity.ok(enquiries);
    }
    
     //API 3: Get enquiry by ID
     // GET /api/enquiries/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Enquiry> getEnquiryById(@PathVariable Long id) {

        Enquiry enquiry = enquiryService.getEnquiryById(id);

        return ResponseEntity.ok(enquiry);
    }
    
    
     // API 4: Update enquiry
     // PUT /api/enquiries/{id}
     @PutMapping("/{id}")
     public ResponseEntity<Enquiry> updateEnquiry(@PathVariable Long id,@RequestBody EnquiryUpdateRequestDto dto) {

     Enquiry updatedEnquiry = enquiryService.updateEnquiry(id, dto);

     return ResponseEntity.ok(updatedEnquiry);
    }
     
     // API 5: Update enquiry status only
    // PATCH /api/enquiries/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Enquiry> updateEnquiryStatus(
          @PathVariable Long id,
          @RequestBody EnquiryStatusUpdateDto dto) {

      Enquiry updatedEnquiry = enquiryService.updateEnquiryStatus(id, dto);

      return ResponseEntity.ok(updatedEnquiry);
  }

   //API 6: Delete enquiry
   //DELETE /api/enquiries/{id}
   @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEnquiry(@PathVariable Long id) {

   enquiryService.deleteEnquiry(id);

   return ResponseEntity.noContent().build(); 
 }
}
