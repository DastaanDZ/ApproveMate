package com.am.approvemate.controller;

import com.am.approvemate.model.Library;
import com.am.approvemate.model.LicensingRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

@RestController
public class AMController {

    @GetMapping("/getDetails")
    public ResponseEntity<List<Library>> sendDetails(){
        try {
            InputStream inputStream = new ClassPathResource("static/updated_java_libraries.json").getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Library> libraries = mapper.readValue(inputStream, new TypeReference<List<Library>>() {});
            return ResponseEntity.ok(libraries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getLicensingRecord")
    public ResponseEntity<List<LicensingRecord>> sendLicensingRecord(){
        try {
            InputStream inputStream = new ClassPathResource("static/licensing_data_synced_with_libraries.json").getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<LicensingRecord> licensingRecords = mapper.readValue(inputStream, new TypeReference<List<LicensingRecord>>() {});
            return ResponseEntity.ok(licensingRecords);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
