package com.bam.controller;

import com.bam.bean.BamUser;
import com.bam.bean.Force;
import com.bam.service.BamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/api/v1/Salesforce/")
public class SalesforceAuthController {

    @Autowired
    private Force force;

    @Autowired
    private BamUserService bamUserService;

    /**
     * Gets the user's Salesforce information and populates the Bam database with that information.
     * @param principal
     * @return The BamUser object.
     */
    @RequestMapping(value="salesforceSync", method=RequestMethod.GET)
    public ResponseEntity<BamUser> salesforceSync(OAuth2Authentication principal) {
        BamUser bamUser = force.getCurrentBamUser(principal);
        bamUserService.addOrUpdateUser(bamUser);
        return ResponseEntity.ok(bamUser);
    }
}
