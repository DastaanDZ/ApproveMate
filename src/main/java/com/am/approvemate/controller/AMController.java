package com.am.approvemate.controller;

import com.am.approvemate.model.Library;
import com.am.approvemate.model.LicensingRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AMController {

    @GetMapping("/getDetails")
    public ResponseEntity<List<Library>> sendDetails(){
        try {
            InputStream inputStream = new ClassPathResource("static/third_party_libraries_sample.json").getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Library> libraries = mapper.readValue(inputStream, new TypeReference<List<Library>>() {});
            return ResponseEntity.ok(libraries);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getLicensingRecord")
    public ResponseEntity<List<LicensingRecord>> sendLicensingRecord(){
        try {
            InputStream inputStream = new ClassPathResource("static/ltid_baid_mappings.json").getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<LicensingRecord> licensingRecords = mapper.readValue(inputStream, new TypeReference<List<LicensingRecord>>() {});
            return ResponseEntity.ok(licensingRecords);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getCombinedLRAndBa")
    public ResponseEntity<List<Map<String, Object>>> sendLRandBADetails() throws Exception {
        List<Map<String, Object>> combinedLRAndBAList = new ArrayList<>();

        // Load Libraries
        InputStream inputStream = new ClassPathResource("static/third_party_libraries_sample.json").getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        List<Library> libraries = mapper.readValue(inputStream, new TypeReference<List<Library>>() {});

        // Load Licensing Records
        InputStream inputStream2 = new ClassPathResource("static/ltid_baid_mappings.json").getInputStream();
        List<LicensingRecord> licensingRecords = mapper.readValue(inputStream2, new TypeReference<List<LicensingRecord>>() {});

        // Combine
        for (LicensingRecord licensingRecord : licensingRecords) {
            String ltId = licensingRecord.getLtID();

            for (Library library : libraries) {
                if (library.getLic_tech_id().equals(ltId)) {
                    Map<String, Object> combined = new LinkedHashMap<>();

                    // Add LicensingRecord fields
                    combined.put("ltID", licensingRecord.getLtID());
                    combined.put("baID", licensingRecord.getBaID());
                    combined.put("productsScopeOfBA", licensingRecord.getProductsScopeOfBA());
                    combined.put("initialUseCase", licensingRecord.getInitialUseCase());

                    // Add Library fields
                    combined.put("plsTechnologyName", library.getPlsTechnologyName());
                    combined.put("versionedArtifact", library.getVersionedArtifact());
                    combined.put("groupid", library.getGroupid());
                    combined.put("name", library.getName());
                    combined.put("version", library.getVersion());
                    combined.put("lic_tech_id_Expiry_Date", library.getLic_tech_id_Expiry_Date());
                    combined.put("copyright_holder", library.getCopyright_holder());
                    combined.put("vendorLatestVersion", library.getVendorLatestVersion());
                    combined.put("plsLatestVersion", library.getPlsLatestVersion());
                    combined.put("sha1cksum", library.getSha1cksum());
                    combined.put("techReleaseDate", library.getTechReleaseDate());
                    combined.put("competitive", library.isCompetitive());
                    combined.put("cryptoFunction", library.isCryptoFunction());
                    combined.put("banned", library.isBanned());
                    combined.put("sourceExists", library.isSourceExists());
                    combined.put("builtAtOracle", library.isBuiltAtOracle());

                    combined.put("vulnerabilities", library.getVulnerabilities());
                    combined.put("licenses", library.getLicenses());
                    combined.put("dependencies", library.getDependencies());

                    combinedLRAndBAList.add(combined);
                }
            }
        }

        return ResponseEntity.ok(combinedLRAndBAList);
    }
}
