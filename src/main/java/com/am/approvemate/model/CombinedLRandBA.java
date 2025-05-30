package com.am.approvemate.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class CombinedLRandBA {

    public String plsTechnologyName;
    public String versionedArtifact;
    public String groupid;
    public String name;
    public String version;
    public String lic_tech_id_Expiry_Date;
    public String copyright_holder;
    public String vendorLatestVersion;
    public String plsLatestVersion;
    public String sha1cksum;
    public String techReleaseDate;
    public boolean competitive;
    public boolean cryptoFunction;
    public boolean banned;
    public boolean sourceExists;
    public boolean builtAtOracle;

    public List<Library.Vulnerability> vulnerabilities;
    public List<Library.License> licenses;
    public Map<String, Library.Dependency> dependencies;

    public static class Vulnerability {
        public String cveSeverity;
        public String cvssV3BaseSeverity;
    }

    public static class License {
        public String licenseType;
        public String spdxLicenseIdentifier;
    }

    public static class Dependency {
        public String scope;
    }

    private String ltID;

    private String baID;

    private String productsScopeOfBA;

    private String initialUseCase;
}
